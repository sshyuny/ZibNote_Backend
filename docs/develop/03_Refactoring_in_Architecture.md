# 아키텍처 구성시 고민점 및 리팩토링

## 🥦 배경
- 깔끔한 아키텍처를 위해 코드를 리팩토링한다.

## 🥦 내용

### 1. Repository단에 있던 로직을 Service 단으로 옮기기
- Repository단에 어울리는 로직인지, Service단에 어울리는 로직인지 고민해보고 적절하지 않으면 리팩토링 진행!

#### (예시 1) validator 로직 
- 저장할 데이터를 검증하는 도메인 validator가 기존에는 repository에 있었으나 Service로 옮긴다.
    - `searchStructure.validate();` 를 `SearchStructurePersistenceAdapter`에서 `SearchStructureService`로 옮김

#### (예시 2) 데이터 후처리 로직
- 데이터를 조회한 뒤 후처리를 거치는 로직이 Repository에 있었으나, Repository는 데이터를 조회하는 것 까지만 하도록 작게 나누고, 후처리 로직을 Service단으로 옮긴다!
    - `List<Structure> findByAddressContaining(String keyword)` 메서드에 있는 로직을 Service로 옮기고, 해당 메서드는 데이터를 단순 조회하는 두 메서드 `List<Structure> findByNumberAddressContaining(String keyword)`, `List<Structure> findByRoadAddressContaining(String keyword)` 으로 쪼갬.

### 2. 컨트롤러 DTO 리팩토링
- 초반부 테스트를 간편하게 하기 위해 DTO에도 `@Builder`를 사용하였으나, 명확한 DTO 사용을 위해 이를 제거하고 사용할 어노테이션을 정리한다.
    - `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor` 사용
    - `@Builder` 사용 하지 않음!

### 3. 서비스 객체 책임 분리
#### 3.1. 배경
- `SearchStructureNote` 도메인은 `SearchStructure` 도메인을 참조하고 있으며, 이 `SearchStructureNote` 도메인의 소유자는 참조 도메인인 `SearchStructure`의 소유자와 동일하다.
- 때문에 `SearchStructureNoteService`에서 소유자를 검증할 때에는, 참조 도메인의 검증 로직인 `searchStructureUseCase.assertSearchStructureOwner`를 그대로 사용해왔다.
- 예시
```java
public class SearchStructureNoteService implements SearchStructureNoteUseCase {

    @Override
    public void updateSearchStructureNote(final SearchStructureNote searchStructureNote, final UUID loginedMemberId) {

        // searchStructureUseCase 메서드를 그대로 사용!
        searchStructureUseCase.assertSearchStructureOwner(
            note.getSearchStructure().getSearchStructureId(),loginedMemberId);

    }
}
```

### 3.2. 변경 후
- 그러나 여러 고민을 해보고 아래 이유로 소유자 검증 로직을 `SearchStructureNote`에 별도로 만들기로 결정했다.
    - 책임 분리: 도메인 책임을 `SearchStructureNoteService`로 분리해줌
    - 직관적인 이름: `SearchStructureNoteService`의 코드에서 갑자기 `SearchStructure`의 검증 로직이 나오는 것이 뜬금없을 수 있음
    - 반복 사용: 새로 만든 검증 로직은 softDelete, update 등 여러 곳에서 사용됨
    - 로직 변경 또는 확장 가능성: 이후 `SearchStructureNote`의 소유자 검증 로직에 변화가 생길 수 있음
- 변경 후 코드
```java
public class SearchStructureNoteService implements SearchStructureNoteUseCase {
    @Override
    public void softDeleteSearchStructureNote(final UUID searchStructureNoteId, final UUID loginedMemberId) {

        final SearchStructureNote note = searchStructureNoteRepository.findBySearchStructureNoteId(searchStructureNoteId);

        // [변경 전] searchStructureUseCase 메서드를 그대로 사용!
        //searchStructureUseCase.assertSearchStructureOwner(note.getSearchStructure().getSearchStructureId(),loginedMemberId);
        // [변경 후] 자체 메서드 사용!
        note.validateForUpdate();

        searchStructureNoteRepository.softDeleteBySearchStructureNoteId(searchStructureNoteId);
    }

    @Override
    public void updateSearchStructureNote(final SearchStructureNote searchStructureNote, final UUID loginedMemberId) {

        final SearchStructureNote note = searchStructureNoteRepository.findBySearchStructureNoteId(
            searchStructureNote.getSearchStructureNoteId());

        note.validateForUpdate();

        // [변경 전] searchStructureUseCase 메서드를 그대로 사용!
        //searchStructureUseCase.assertSearchStructureOwner(note.getSearchStructure().getSearchStructureId(),loginedMemberId);
        // [변경 후] 자체 메서드 사용!
        note.validateForUpdate();

        searchStructureNoteRepository.updateBySearchStructureNoteId(searchStructureNote);
    }
}
```