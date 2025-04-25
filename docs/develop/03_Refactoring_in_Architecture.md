# 배경
- 깔끔한 아키텍처를 위해 코드를 리팩토링함

# 내용

## 1. Repository단에 있던 로직을 Service 단으로 옮기기
- Repository단에 어울리는 로직인지, Service단에 어울리는 로직인지 고민해보고 적절하지 않으면 리팩토링함!

### (예시) validator 로직 
- 저장할 데이터를 검증하는 validator가 기존에는 repository에 있었으나 Service로 옮김
```java
// [변경 전]

public class SearchStructurePersistenceAdapter implements SearchStructureRepository {
    
    private final SearchStructureJpaRepository searchStructureJpaRepository;

    @Override
    public Long save(final SearchStructure searchStructure) {

        // 원래 Repository에 있던 validator을 제거하고 Service단으로 옮김!
        searchStructure.validate();

        ...
            
        searchStructureJpaRepository.save(searchStructureEntity);
        return searchStructureEntity.getSearchStructureId();
    }
}
```

### (예시) 데이터 후처리 로직
- 데이터를 조회한 뒤 후처리를 거치는 로직이 Repository에 있었으나, Repository는 데이터를 조회하는 것 까지만 하도록 작게 나누고, 후처리 로직을 Service단으로 옮김!
```java
public class StructurePersistenceAdapter implements StructureRepository {

    @Override
    public List<Structure> findByAddressContaining(final String keyword) {

        List<Structure> domainsByNumberAddress = structureJpaRepository.findByNumberAddressContaining(keyword).stream()
            .map(entity -> StructureMapper.toDomain(entity))
            .collect(Collectors.toList());

        if (domainsByNumberAddress.size() > 10) { return domainsByNumberAddress; }

        List<Structure> domainsByRoadAddress = structureJpaRepository.findByRoadAddressContaining(keyword).stream()
            .map(entity -> StructureMapper.toDomain(entity))
            .collect(Collectors.toList());

        Map<Long, Structure> domains = new HashMap<>();
        Stream.concat(domainsByNumberAddress.stream(), domainsByRoadAddress.stream())
                .forEach(domain -> domains.putIfAbsent(domain.getStructureId(), domain));

        return new ArrayList<>(domains.values());
    }

}
```
