Registrar 0.0.6
- Now supports 26.2
- Add `BlockItemId` (26.2) and `ResourceKey` support for registration
  - `ItemRegistrant` and `DelayedItemRegistrant` can now accept a Class containing `BlockItemId`s for registration via reflection, see `registerFromAnnotations`