Registrar 0.2.3
- BlockPresenter now implements Supplier
  - Added `BlockPresenter#getBlockItemIdOrThrow`
- `IGameRuleRegistrant`s can now specify a default category to add their `GameRule`s to
  - The default category will be used unless an overload that contains a category param is used