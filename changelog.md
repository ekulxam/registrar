Registrar 0.1.0
- Add `SpecialRegistrant` and `special` package
- Add `AttachmentTypeRegistrant`, which extends `SpecialRegistrant`
- `DynamicRegistrant` now extends `SpecialRegistrant`
- Add `GameRuleRegistrant` and `DelayedGameRuleRegistrant` (closes [#1](https://github.com/ekulxam/registrar/issues/1))
- Add `shared` package and interfaces with default methods for regular and delayed registrants
  - This change affects registrants for attributes, block entities, blocks, data component types, entity types, game rules, items, potions, sound events, and status effects.
- Reflective `BlockItem` registration is now available pre-26.2
  - Add `suppressIdWarnings` to `ConstructItem` for 26.2
- Documentation in README