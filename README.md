# Registrar

A registry helper for Minecraft modding

## Adding Registrar To Your Project

<details>
<summary>Modrinth Maven</summary>

If you don't have the Modrinth maven in your `repositories` block, add it like so:

`build.gradle`:
```groovy
maven {
    url("https://api.modrinth.com/maven")
    content {
        includeGroupAndSubgroups("maven.modrinth")
    }
}
```
or `build.gradle.kts`:
```kotlin
maven("https://api.modrinth.com/maven") {
    content {
        includeGroupAndSubgroups("maven.modrinth")
    }
}
```

Then, add this project to your dependencies.

If you're using deobfuscated Minecraft:
```groovy
api("maven.modrinth:registrar:${project.registrar_version}") // or implementation
include("maven.modrinth:registrar:${project.registrar_version}")
```

If you're using obfuscated Minecraft:
```groovy
modApi("maven.modrinth:registrar:${project.registrar_version}") // or modImplementation
include("maven.modrinth:registrar:${project.registrar_version}")
```

</details>

<details>
<summary>mavenLocal</summary>

First, fork or download this repository and publish it to your mavenLocal.

Then, adding the following to your `repositories` block:
```groovy
mavenLocal()
```

Then, add this project to your dependencies.

If you're using deobfuscated Minecraft:
```groovy
api("survivalblock.atmosphere:registrar:${project.registrar_version}") // or implementation
include("survivalblock.atmosphere:registrar:${project.registrar_version}")
```

If you're using obfuscated Minecraft:
```groovy
modApi("survivalblock.atmosphere:registrar:${project.registrar_version}") // or modImplementation
include("survivalblock.atmosphere:registrar:${project.registrar_version}")
```

</details>

## Normal Registrants

To start, create a `Registrant` instance of the type you need, like so:
```java
private static final BlockRegistrant BLOCK_REGISTRANT = new BlockRegistrant(ExampleMod.MOD_ID);
```

If a registrant of the required type does not exist, you can create one!
```java
private static final Registrant RECIPE_SERIALIZER_REGISTRANT = new Registrant(ExampleMod.MOD_ID, BuiltinRegistries.RECIPE_SERIALIZER);
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
For more information, see the [Documentation](#documentation/items).

## Delayed Registrants
All normal registrants have a delayed counterpart, which can be found in the `delayed` package.

Delayed registrants are identical to normal registrants, with one exception:
`DelayedRegistrant#consumeAll` must be called to actually register the objects.
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
    DYNAMIC_REGISTRANT.bootstrap(registryBuilder);
}
```
where `bootstrap` is a shorthand for `registryBuilder.addRegistry(REGISTRY_KEY, DYNAMIC_REGISTRANT::bootstrap);`

## Special Registrants
Special registrants are registrants which have no backing `Registry`. They may or may not
extend `Registrant`. (Note that `GameRuleRegistrant` before 1.21.11 is technically a special
registrant.)

Currently, this only includes `AttachmentTypeRegistrant`.

## Documentation
Registrar provides some javadocs, but if you don't want to download the source jar to avoid my multiversioned comments, read this.

<details>
<summary>Attachment Types</summary>

`AttachmentTypeRegistrant` contains overloads to register with the builder or register with method parameters.

</details>

<details>
<summary>Block Entity Types</summary>

`BlockEntityTypeRegistrant` and `DelayedBlockEntityTypeRegistrant` implement `IBlockEntityTypeRegistrant`,
which can register `BlockEntityType.Builder`/`FabricBlockEntityTypeBuilder`s depending on the version. It
can also take the BE's constructor (as a lambda) and a `Block` varargs to construct the `BlockEntityType`.

</details>

<details>
<summary>Blocks</summary>

`BlockRegistrant` and `DelayedBlockRegistrant` implement `IBlockRegistrant`, which can register `Block`s
based on a constructor and their `BlockBehaviour$Properties`. Note that `BlockBehaviour$Properties#setId`
is called for you.

In 26.2, `createId` methods are available to create `BlockItemId`s.

</details>

<details>
<summary>Game Rules</summary>

Due to `BuiltinRegistries.GAME_RULE` not existing before 1.21.11, `GameRuleRegistrant`'s `registry` is
null in those versions. Registration in those versions is done via Fabric API's `GameRuleRegistry`, which
is not a real `Registry`.

`DelayedGameRuleRegistrant` does not exist before 1.21.11 for reasons described above.

Both `GameRuleRegistrant` and `DelayedGameRuleRegistrant` implement `IGameRuleRegistrant`, which contains
methods to register `GameRules$Key`/`GameRule`s depending on the version. Overloads to register a boolean
rule exist, but due to the larger variation of ways to construct rules pre-1.21.11, these are not exhaustive.
It is recommended to make your own helpers as necessary.

`IGameRuleRegistrant` also contains minor multiversion utilities to obtain values from `GameRules`. These
can also be found in the actual registrants, which perform unboxing for some rules.
</details>

<div id="documentation/items">
<details>
<summary>Items</summary>

`ItemRegistrant` and `DelayedItemRegistrant` implement `IItemRegistrant` (funny double I), which can register
`Item`s based on a constructor and their `Item$Properties`. Note that `Item$Properties#setId` is called for
you.

The two `IItemRegistrant`s are also capable of registering `BlockItem`s with other register overloads. In
addition, reflective registration for `BlockItem`s is also supported with the `ConstructItem` annotation.
This requires the invocation of `IItemRegistrant#registerFromAnnotations`, which returns a map of the `Block`s
to registered `Item`s. The class parameter dictates which class's fields to check, while `tryAllByDefault`
will tell the registrant to construct items even if no annotation is present.

Setting `useBlockTranslation` to true in `ConstructItem` will invoke `useBlockDescriptionPrefix` on the
`Item$Properties`. This isn't necessary in 1.21.1, because it is [determined by `BlockItem#getDescriptionId`](https://mcsrc.dev/1/1.21.1/net/minecraft/world/item/BlockItem#L181).

You can also exclude the item from being automatically registered with `exclude = true`. This is helpful when
manual registration is required for `BlockItem`s that have more than `Block block, Item.Properties properties`
in their  constructors (such as signs and banners), because these are unsupported by the reflective registration.
For classes with a constructor with those parameters, you can specify the class with
`constructor = CustomBlockItem.class`.

`ConstructItem` must be annotated on a `public static` `Block` or `BlockItemId` field. Note that annotating a
`Block` field in this way beyond 26.2 is deprecated and will result in spammed warnings in your logs unless you
set `suppressIdWarnings = true` in the annotation.

</details>
</div>