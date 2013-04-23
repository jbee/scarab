package se.jbee.jvm.graph;

import se.jbee.jvm.Annotation;
import se.jbee.jvm.Archive;
import se.jbee.jvm.Class;
import se.jbee.jvm.Field;
import se.jbee.jvm.Method;
import se.jbee.jvm.Package;
import se.jbee.jvm.Packages;
import se.jbee.jvm.Parameter;
import se.jbee.jvm.reflect.ClassDeclaration;
import se.jbee.jvm.reflect.ClassProcessor;

public final class ClassGraph
		implements ClassProcessor {

	/**
	 * All (root) packages considered to be the basis, the "own" code.
	 */
	public final Packages basePackages;
	public final Edges<Archive, ArchiveNode> archives = new Edges<Archive, ArchiveNode>();
	public final Edges<Package, PackageNode> packages = new Edges<Package, PackageNode>();
	public final Edges<Class, ClassNode> classes = new Edges<Class, ClassNode>();
	public final Edges<Field, FieldNode> fields = new Edges<Field, FieldNode>();
	public final Edges<Method, MethodNode> methods = new Edges<Method, MethodNode>();
	public final Edges<Method, OverrideNode> overrides = new Edges<Method, OverrideNode>();
	public final Edges<Annotation, AnnotationNode> annotations = new Edges<Annotation, AnnotationNode>();
	public final Edges<Parameter, ParameterNode> parameters = new Edges<Parameter, ParameterNode>();

	public ClassGraph( Packages basePackages ) {
		super();
		this.basePackages = basePackages;
	}

	@Override
	public void process( ClassDeclaration cls ) {
		cls( cls.cls ).declare( cls );
	}

	public ArchiveNode archive( Archive archive ) {
		ArchiveNode node = archives.node( archive );
		if ( node == null ) {
			node = new ArchiveNode( this, archive, archives.count() );
			archives.add( node );
		}
		return node;
	}

	public ClassNode cls( Class type ) {
		ClassNode node = classes.node( type );
		if ( node == null ) {
			node = new ClassNode( this, type, classes.count() );
			classes.add( node );
		}
		return node;
	}

	public PackageNode pkg( Package pkg ) {
		PackageNode node = packages.node( pkg );
		if ( node == null ) {
			node = new PackageNode( this, pkg, packages.count() );
			packages.add( node );
		}
		return node;
	}

	public MethodNode method( Method method ) {
		MethodNode node = methods.node( method );
		if ( node == null ) {
			node = new MethodNode( this, method, methods.count() );
			methods.add( node );
		}
		return node;
	}

	public OverrideNode override( Method method ) {
		method = method.declaredBy( Class.NONE );
		OverrideNode node = overrides.node( method );
		if ( node == null ) {
			node = new OverrideNode( this, method, overrides.count() );
			overrides.add( node );
		}
		return node;
	}

	public FieldNode field( Field field ) {
		FieldNode node = fields.node( field );
		if ( node == null ) {
			node = new FieldNode( this, field, fields.count() );
			fields.add( node );
		}
		return node;
	}

	public AnnotationNode annotation( Class annotation ) {
		return annotation( Annotation.annotation( annotation ) );
	}

	public AnnotationNode annotation( Annotation annotation ) {
		AnnotationNode node = annotations.node( annotation );
		if ( node == null ) {
			node = new AnnotationNode( this, annotation, annotations.count() );
			annotations.add( node );
		}
		return node;
	}

	public ParameterNode parameter( Parameter parameter ) {
		ParameterNode node = parameters.node( parameter );
		if ( node == null ) {
			node = new ParameterNode( this, parameter, parameters.count() );
			parameters.add( node );
		}
		return node;
	}

}
