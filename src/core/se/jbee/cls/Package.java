package se.jbee.cls;

public final class Package {

	public static final Package DEFAULT = new Package( "" );

	public static Package pkg( String name ) {
		return name == null || name.isEmpty()
			? DEFAULT
			: new Package( name );
	}

	public final String name;

	private Package( String name ) {
		super();
		this.name = name.intern();
	}

	public boolean hasParent() {
		return name != DEFAULT.name;
	}

	public int levels() {
		if ( !hasParent() ) {
			return 0;
		}
		int c = 0;
		int i = name.indexOf( '/', 0 );
		while ( i > 0 ) {
			c++;
			i = name.indexOf( '/', i + 1 );
		}
		return c + 1;
	}

	public Package parent() {
		if ( !hasParent() ) {
			return this;
		}
		int idx = name.lastIndexOf( '/' );
		return idx < 0
			? DEFAULT
			: pkg( name.substring( 0, idx ) );
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Package && equalTo( (Package) obj );
	}

	public boolean equalTo( Package other ) {
		return name == other.name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public String canonicalName() {
		return name.replace( '/', '.' );
	}

	@Override
	public String toString() {
		return isDefault()
			? "(default)"
			: canonicalName();
	}

	public boolean isDefault() {
		return name == DEFAULT.name;
	}

	public Class classWithSimpleName( String simpleName ) {
		return Class.cls( name + "/" + simpleName );
	}
}
