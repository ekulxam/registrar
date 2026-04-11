# Registrar

A registry helper for Minecraft modding

## Normal Registrants

To start, create a `Registrant` instance of the type you need, like so:
```java
private static final BlockRegistrant BLOCK_REGISTRANT = new BlockRegistrant(MyMod.ID);
```

If a registrant of the required type does not exist, you can create one!
```java
private static final Registrant RECIPE_SERIALIZER_REGISTRANT = new Registrant(MyMod.ID, BuiltinRegistries.RECIPE_SERIALIZER);
```
Feel free to open an issue to notify me if there is a need to add other common registrants.

To register an object, simply call `Registrant#register`.
```java
REGISTRANT.register("name", THING);
```

Note that some registrant classes have special register methods.
For example, `ItemRegistrant` has a register method that takes three parameters.
```java
ITEM_REGISTRANT.register("name", Item::new, new Item.Properties());
```
`ItemRegistrant` is also capable of registering `BlockItem`s via separate `register` overloads.

## Delayed Registrants
All normal registrants have a delayed counterpart, which can be found in the `delayed` package.

Delayed registrants are identical to normal registrants, with one exception.
You must call `DelayedRegistrant#consumeAll` to actually register the objects.
```java
public static void init() {
  REGISTRANT.consumeAll();
}
```

## Dynamic Registrants
Dynamic registrants are registrants for dynamic registries.
Currently, only registrants for `DamageType`s and `Enchantment`s exist in the base library.
See these links for examples of registering [damage types](https://github.com/ekulxam/amarong/blob/2689ce94d4d3daa728b40fd0e943e89aeae78e84/src/main/java/survivalblock/amarong/common/init/AmarongDamageTypes.java#L12) and [enchantments](https://github.com/ekulxam/amarong/blob/2689ce94d4d3daa728b40fd0e943e89aeae78e84/src/main/java/survivalblock/amarong/common/init/AmarongEnchantments.java#L13).

To add the registrants for datagen, override `buildRegistry` in your mod's `DataGeneratorEntrypoint` like so:
```java
@Override
public void buildRegistry(RegistrySetBuilder registryBuilder) {
    registryBuilder.addRegistry(REGISTRY_KEY, DYNAMIC_REGISTRANT::bootstrap);
}
```