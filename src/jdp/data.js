var data = {
	"name": "se.jbee.inject",
	"children": [
{
	"name": "se.jbee.inject.bind",
	"children": [
		{"name": "TypedElementBinder","type": "class", "c_method": 9, "c_field": 0, "c_references": 15, "c_referencedBy": 3},
		{"name": "ReferenceSupplier","type": "final class", "c_method": 2, "c_field": 1, "c_references": 11, "c_referencedBy": 2},
		{"name": "ArrayBridgeSupplier","type": "abstract class", "c_method": 4, "c_field": 0, "c_references": 13, "c_referencedBy": 4},
		{"name": "LazyResolvedDependencyProvider","type": "final class", "c_method": 3, "c_field": 2, "c_references": 14, "c_referencedBy": 3},
		{"name": "TypedBinder","type": "class", "c_method": 25, "c_field": 2, "c_references": 35, "c_referencedBy": 13},
		{"name": "RootBinder","type": "class", "c_method": 9, "c_field": 0, "c_references": 16, "c_referencedBy": 7},
		{"name": "ElementsSupplier","type": "final class", "c_method": 3, "c_field": 2, "c_references": 17, "c_referencedBy": 2},
		{"name": "BinderModuleWith","type": "abstract class", "c_method": 5, "c_field": 0, "c_references": 18, "c_referencedBy": 1},
		{"name": "SuppliedBy","type": "final class", "c_method": 12, "c_field": 0, "c_references": 44, "c_referencedBy": 24},
		{"name": "LoggerFactory","type": "class", "c_method": 3, "c_field": 0, "c_references": 14, "c_referencedBy": 2},
		{"name": "Binder","type": "class", "c_method": 29, "c_field": 5, "c_references": 39, "c_referencedBy": 17},
		{"name": "FactorySupplier","type": "final class", "c_method": 2, "c_field": 1, "c_references": 12, "c_referencedBy": 2},
		{"name": "ParametrizedInstanceSupplier","type": "final class", "c_method": 3, "c_field": 1, "c_references": 16, "c_referencedBy": 2},
		{"name": "AbstractBinderModule","type": "abstract class", "c_method": 32, "c_field": 1, "c_references": 34, "c_referencedBy": 3},
		{"name": "SetBridgeModule","type": "class", "c_method": 2, "c_field": 0, "c_references": 17, "c_referencedBy": 2},
		{"name": "TargetedBinder","type": "class", "c_method": 10, "c_field": 0, "c_references": 22, "c_referencedBy": 4},
		{"name": "AutobindBindings","type": "class", "c_method": 2, "c_field": 1, "c_references": 15, "c_referencedBy": 2},
		{"name": "StaticConstructorSupplier","type": "final class", "c_method": 2, "c_field": 2, "c_references": 13, "c_referencedBy": 2},
		{"name": "ArrayToListBridgeSupplier","type": "final class", "c_method": 3, "c_field": 0, "c_references": 9, "c_referencedBy": 2},
		{"name": "ProviderBridgeModule","type": "class", "c_method": 2, "c_field": 0, "c_references": 17, "c_referencedBy": 2},
		{"name": "ProviderAsSupplier","type": "final class", "c_method": 2, "c_field": 1, "c_references": 8, "c_referencedBy": 2},
		{"name": "ScopedBinder","type": "class", "c_method": 6, "c_field": 0, "c_references": 21, "c_referencedBy": 11},
		{"name": "ConstructorSupplier","type": "final class", "c_method": 2, "c_field": 2, "c_references": 17, "c_referencedBy": 2},
		{"name": "BuildinBundle","type": "enum", "c_method": 5, "c_field": 0, "c_references": 19, "c_referencedBy": 6},
		{"name": "LoggerModule","type": "class", "c_method": 2, "c_field": 0, "c_references": 17, "c_referencedBy": 2},
		{"name": "FactoryMethodSupplier","type": "final class", "c_method": 2, "c_field": 5, "c_references": 25, "c_referencedBy": 2},
		{"name": "CollectionBridgeModule","type": "class", "c_method": 2, "c_field": 0, "c_references": 17, "c_referencedBy": 2},
		{"name": "ArrayToSetBridgeSupplier","type": "final class", "c_method": 3, "c_field": 0, "c_references": 10, "c_referencedBy": 2},
		{"name": "ConstantSupplier","type": "final class", "c_method": 3, "c_field": 1, "c_references": 7, "c_referencedBy": 2},
		{"name": "BinderModule","type": "abstract class", "c_method": 6, "c_field": 0, "c_references": 16, "c_referencedBy": 8},
		{"name": "ProviderSupplier","type": "final class", "c_method": 4, "c_field": 0, "c_references": 13, "c_referencedBy": 2},
		{"name": "InstanceSupplier","type": "final class", "c_method": 3, "c_field": 1, "c_references": 12, "c_referencedBy": 2},
		{"name": "InspectBinder","type": "class", "c_method": 9, "c_field": 2, "c_references": 36, "c_referencedBy": 4},
		{"name": "ListBridgeModule","type": "class", "c_method": 2, "c_field": 0, "c_references": 17, "c_referencedBy": 2}]
}
,
{
	"name": "se.jbee.inject.util",
	"children": [
		{"name": "Factory","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 7},
		{"name": "ThreadScope","type": "final class", "c_method": 4, "c_field": 2, "c_references": 13, "c_referencedBy": 2},
		{"name": "KeyDeductionScope","type": "final class", "c_method": 3, "c_field": 1, "c_references": 12, "c_referencedBy": 2},
		{"name": "Typecast","type": "final class", "c_method": 15, "c_field": 0, "c_references": 13, "c_referencedBy": 1},
		{"name": "KeyDeduction","type": "interface", "c_method": 1, "c_field": 0, "c_references": 3, "c_referencedBy": 6},
		{"name": "Inject","type": "final class", "c_method": 4, "c_field": 0, "c_references": 17, "c_referencedBy": 6},
		{"name": "SuppliableSource","type": "class", "c_method": 4, "c_field": 1, "c_references": 34, "c_referencedBy": 2},
		{"name": "SupplierToInjectable","type": "class", "c_method": 2, "c_field": 2, "c_references": 13, "c_referencedBy": 2},
		{"name": "DependencyTypeAsKey","type": "final class", "c_method": 3, "c_field": 0, "c_references": 11, "c_referencedBy": 2},
		{"name": "Suppliable","type": "final class", "c_method": 4, "c_field": 5, "c_references": 14, "c_referencedBy": 4},
		{"name": "TargetInstanceAsKey","type": "final class", "c_method": 3, "c_field": 0, "c_references": 13, "c_referencedBy": 2},
		{"name": "Provider","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 7},
		{"name": "Argument","type": "final class", "c_method": 16, "c_field": 4, "c_references": 25, "c_referencedBy": 4},
		{"name": "KeyDeductionRepository","type": "final class", "c_method": 2, "c_field": 2, "c_references": 16, "c_referencedBy": 3},
		{"name": "SourcedInjector","type": "final class", "c_method": 12, "c_field": 1, "c_references": 44, "c_referencedBy": 2},
		{"name": "Scoped","type": "class", "c_method": 4, "c_field": 0, "c_references": 18, "c_referencedBy": 21},
		{"name": "Value","type": "final class", "c_method": 5, "c_field": 2, "c_references": 10, "c_referencedBy": 2},
		{"name": "SnapshotingInjectable","type": "final class", "c_method": 2, "c_field": 2, "c_references": 11, "c_referencedBy": 2},
		{"name": "ResourceRepository","type": "final class", "c_method": 2, "c_field": 1, "c_references": 11, "c_referencedBy": 3},
		{"name": "ApplicationScope","type": "final class", "c_method": 3, "c_field": 0, "c_references": 6, "c_referencedBy": 2},
		{"name": "StaticInjectron","type": "class", "c_method": 6, "c_field": 6, "c_references": 30, "c_referencedBy": 2},
		{"name": "Metaclass","type": "final class", "c_method": 5, "c_field": 1, "c_references": 20, "c_referencedBy": 10},
		{"name": "InjectronSource","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 5},
		{"name": "SnapshotRepository","type": "final class", "c_method": 2, "c_field": 2, "c_references": 10, "c_referencedBy": 3},
		{"name": "InjectionScope","type": "final class", "c_method": 4, "c_field": 0, "c_references": 9, "c_referencedBy": 2}]
}
,
{
	"name": "se.jbee.inject.config",
	"children": [
		{"name": "Options","type": "final class", "c_method": 9, "c_field": 1, "c_references": 15, "c_referencedBy": 4},
		{"name": "PackagesEdition","type": "class", "c_method": 2, "c_field": 1, "c_references": 11, "c_referencedBy": 2},
		{"name": "FeatureEdition","type": "class", "c_method": 2, "c_field": 2, "c_references": 13, "c_referencedBy": 2},
		{"name": "Feature","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 3},
		{"name": "Presets","type": "final class", "c_method": 7, "c_field": 1, "c_references": 14, "c_referencedBy": 5},
		{"name": "Globals","type": "final class", "c_method": 9, "c_field": 3, "c_references": 17, "c_referencedBy": 5},
		{"name": "Edition","type": "interface", "c_method": 2, "c_field": 0, "c_references": 5, "c_referencedBy": 6},
		{"name": "Edition$1","type": "final class", "c_method": 2, "c_field": 0, "c_references": 4, "c_referencedBy": 2}]
}
,
{
	"name": "se.jbee.inject.bootstrap",
	"children": [
		{"name": "ModularBootstrapperBundle","type": "abstract class", "c_method": 5, "c_field": 1, "c_references": 17, "c_referencedBy": 1},
		{"name": "Link","type": "final class", "c_method": 4, "c_field": 0, "c_references": 17, "c_referencedBy": 4},
		{"name": "Modulariser","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 3},
		{"name": "Bindings","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 14},
		{"name": "Bootstrapper","type": "interface", "c_method": 7, "c_field": 0, "c_references": 3, "c_referencedBy": 13},
		{"name": "LazyPresetModule","type": "final class", "c_method": 2, "c_field": 2, "c_references": 17, "c_referencedBy": 2},
		{"name": "Inspect","type": "class", "c_method": 18, "c_field": 7, "c_references": 44, "c_referencedBy": 5},
		{"name": "Invoke","type": "final class", "c_method": 3, "c_field": 0, "c_references": 16, "c_referencedBy": 6},
		{"name": "ModularBootstrapper","type": "interface", "c_method": 1, "c_field": 0, "c_references": 3, "c_referencedBy": 9},
		{"name": "Module","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 9},
		{"name": "InspectorModule","type": "final class", "c_method": 3, "c_field": 2, "c_references": 13, "c_referencedBy": 2},
		{"name": "Bootstrap$BuildinBootstrapper$2","type": "class", "c_method": 3, "c_field": 2, "c_references": 13, "c_referencedBy": 2},
		{"name": "Bootstrap$BuildinBootstrapper$1","type": "class", "c_method": 3, "c_field": 3, "c_references": 14, "c_referencedBy": 2},
		{"name": "Bootstrap$BuildinBootstrapper$3","type": "class", "c_method": 3, "c_field": 2, "c_references": 13, "c_referencedBy": 2},
		{"name": "ModularBundle","type": "interface", "c_method": 1, "c_field": 0, "c_references": 4, "c_referencedBy": 4},
		{"name": "BootstrapperBundle","type": "abstract class", "c_method": 16, "c_field": 1, "c_references": 29, "c_referencedBy": 2},
		{"name": "BuildinBootstrapper","type": "class", "c_method": 12, "c_field": 6, "c_references": 54, "c_referencedBy": 5},
		{"name": "ListBindings","type": "class", "c_method": 2, "c_field": 1, "c_references": 15, "c_referencedBy": 3},
		{"name": "SuppliableLinker","type": "class", "c_method": 8, "c_field": 1, "c_references": 53, "c_referencedBy": 2},
		{"name": "Bundle","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 6},
		{"name": "Linker","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 3},
		{"name": "Binding","type": "final class", "c_method": 4, "c_field": 4, "c_references": 22, "c_referencedBy": 3},
		{"name": "Bootstrap","type": "final class", "c_method": 13, "c_field": 0, "c_references": 49, "c_referencedBy": 10},
		{"name": "PresetModule","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 6},
		{"name": "Inspector","type": "interface", "c_method": 4, "c_field": 0, "c_references": 2, "c_referencedBy": 18},
		{"name": "Bundler","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 2}]
}
,
{
	"name": "se.jbee.inject.service",
	"children": [
		{"name": "ServiceInvocation","type": "interface", "c_method": 3, "c_field": 0, "c_references": 3, "c_referencedBy": 4},
		{"name": "ServiceMethodProvider","type": "final class", "c_method": 5, "c_field": 5, "c_references": 40, "c_referencedBy": 3},
		{"name": "Extension","type": "interface", "c_method": 0, "c_field": 0, "c_references": 2, "c_referencedBy": 3},
		{"name": "ServiceMethodModule","type": "final class", "c_method": 2, "c_field": 0, "c_references": 24, "c_referencedBy": 2},
		{"name": "ServiceInvocationExtension","type": "enum", "c_method": 4, "c_field": 0, "c_references": 12, "c_referencedBy": 4},
		{"name": "ServiceClassExtension","type": "enum", "c_method": 4, "c_field": 0, "c_references": 11, "c_referencedBy": 4},
		{"name": "PreresolvingServiceMethod","type": "final class", "c_method": 13, "c_field": 9, "c_references": 42, "c_referencedBy": 3},
		{"name": "ServiceModule","type": "abstract class", "c_method": 9, "c_field": 1, "c_references": 41, "c_referencedBy": 6},
		{"name": "ServiceMethod","type": "interface", "c_method": 1, "c_field": 0, "c_references": 3, "c_referencedBy": 6},
		{"name": "ServiceProvider","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 4},
		{"name": "ServiceProviderSupplier","type": "final class", "c_method": 3, "c_field": 0, "c_references": 9, "c_referencedBy": 3},
		{"name": "ServiceSupplier","type": "final class", "c_method": 3, "c_field": 0, "c_references": 15, "c_referencedBy": 3},
		{"name": "ExtensionModule","type": "abstract class", "c_method": 6, "c_field": 0, "c_references": 21, "c_referencedBy": 4}]
}
,
		{"name": "Expiry","type": "final class", "c_method": 9, "c_field": 1, "c_references": 11, "c_referencedBy": 10},
		{"name": "Repository","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 12},
		{"name": "Named","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 4},
		{"name": "Emergence","type": "final class", "c_method": 5, "c_field": 2, "c_references": 11, "c_referencedBy": 5},
		{"name": "DeclarationType","type": "enum", "c_method": 8, "c_field": 0, "c_references": 12, "c_referencedBy": 6},
		{"name": "PreciserThanComparator","type": "class", "c_method": 3, "c_field": 0, "c_references": 7, "c_referencedBy": 2},
		{"name": "NoSuchFunctionException","type": "final class", "c_method": 1, "c_field": 0, "c_references": 9, "c_referencedBy": 3},
		{"name": "Supplier","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 29},
		{"name": "NoSuchResourceException","type": "final class", "c_method": 1, "c_field": 0, "c_references": 11, "c_referencedBy": 4},
		{"name": "Resource","type": "final class", "c_method": 18, "c_field": 2, "c_references": 23, "c_referencedBy": 15},
		{"name": "PreciserThan","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 14},
		{"name": "Typed","type": "interface", "c_method": 2, "c_field": 0, "c_references": 2, "c_referencedBy": 4},
		{"name": "Resourcing","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 5},
		{"name": "Demand","type": "final class", "c_method": 8, "c_field": 4, "c_references": 13, "c_referencedBy": 13},
		{"name": "Parameter","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 10},
		{"name": "Source","type": "final class", "c_method": 8, "c_field": 2, "c_references": 13, "c_referencedBy": 14},
		{"name": "ResourcingComparator","type": "final class", "c_method": 3, "c_field": 0, "c_references": 16, "c_referencedBy": 2},
		{"name": "Instance","type": "final class", "c_method": 18, "c_field": 2, "c_references": 20, "c_referencedBy": 26},
		{"name": "Array","type": "final class", "c_method": 7, "c_field": 0, "c_references": 13, "c_referencedBy": 9},
		{"name": "Injectron","type": "interface", "c_method": 3, "c_field": 0, "c_references": 3, "c_referencedBy": 9},
		{"name": "Packages","type": "final class", "c_method": 26, "c_field": 3, "c_references": 20, "c_referencedBy": 7},
		{"name": "MoreFrequentExpiryException","type": "final class", "c_method": 1, "c_field": 0, "c_references": 10, "c_referencedBy": 4},
		{"name": "Scope","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 22},
		{"name": "Type","type": "final class", "c_method": 55, "c_field": 3, "c_references": 52, "c_referencedBy": 38},
		{"name": "DIRuntimeException","type": "class", "c_method": 4, "c_field": 0, "c_references": 27, "c_referencedBy": 9},
		{"name": "DependencyCycleException","type": "final class", "c_method": 1, "c_field": 0, "c_references": 9, "c_referencedBy": 4},
		{"name": "Name","type": "final class", "c_method": 15, "c_field": 1, "c_references": 22, "c_referencedBy": 15},
		{"name": "Precision","type": "final class", "c_method": 5, "c_field": 0, "c_references": 8, "c_referencedBy": 9},
		{"name": "Injection","type": "final class", "c_method": 4, "c_field": 2, "c_references": 12, "c_referencedBy": 4},
		{"name": "Injector","type": "interface", "c_method": 1, "c_field": 0, "c_references": 6, "c_referencedBy": 20},
		{"name": "Target","type": "final class", "c_method": 22, "c_field": 3, "c_references": 24, "c_referencedBy": 7},
		{"name": "Dependency","type": "final class", "c_method": 32, "c_field": 2, "c_references": 36, "c_referencedBy": 30},
		{"name": "Instances","type": "final class", "c_method": 12, "c_field": 1, "c_references": 14, "c_referencedBy": 2},
		{"name": "Injectable","type": "interface", "c_method": 1, "c_field": 0, "c_references": 2, "c_referencedBy": 11}]
}
;