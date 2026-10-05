Registrar 0.2.2
- Fix `@ConstructBlock`
  - Correct type checks
  - `IBlockRegistrant#registerAndPresent` and overloads now exist to obtain `BlockPresenter`s that contain the block and key
- `@ConstructItem` now works on `BlockPresenter`s without calling `IItemRegistrant#registerFromAnnotations`