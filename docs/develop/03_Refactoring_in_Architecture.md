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
