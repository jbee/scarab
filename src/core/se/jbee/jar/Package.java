package se.jbee.jar;

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

	public Package parent() {
		return !hasParent()
			? this
			: pkg( name.substring( 0, name.lastIndexOf( '/' ) ) );
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

	@Override
	public String toString() {
		return isDefault()
			? "(default)"
			: name;
	}

	public boolean isDefault() {
		return name == DEFAULT.name;
	}
}
