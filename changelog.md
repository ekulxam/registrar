Registrar 0.1.4
- Add an overloaded `registerFromAnnotations` to IItemRegistrant
  - Allows specifying Fields to be registered
  - Defaults to `Class#getDeclaredFields`
- Add methods to construct tags