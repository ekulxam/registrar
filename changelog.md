Registrar 0.2.0
- Add an overloaded `registerFromAnnotations` to IItemRegistrant
  - Allows specifying Fields to be registered
  - Defaults to `Class#getDeclaredFields`
- Add methods to construct tags
- Add `ConstructBlock` annotation
  - Annotated on blocks, allowing `IBlockRegistrant` to register the block by deriving the block's name/id from the field name
- Add basic worldgen (structure) registrants
  - Split Registrar for 1.21.10 into Registrar for 1.21.8 and Registrar for 1.21.10
- Add testmod (does or should not affect the end user)