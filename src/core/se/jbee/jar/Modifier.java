package se.jbee.jar;

public enum Modifier {

	ACC_PUBLIC( 0x0001 ), //	Klasse ist als public deklariert, daher Zugriff auch von außerhalb ihres Pakets erlaubt.
	ACC_FINAL( 0x0010 ), //	Klasse ist als final deklariert, keine Unterklassen erlaubt.
	ACC_SUPER( 0x0020 ), //	Besondere Behandlung für Methoden der Superklasse beim Aufruf über invokespecial.
	ACC_INTERFACE( 0x0200 ), // class-File beschreibt ein Interface, keine Klasse. ACC_ABSTRACT erforderlich.
	ACC_ABSTRACT( 0x0400 ), //Klasse ist als abstract deklariert, daher keine Instanziierung erlaubt.
	ACC_SYNTHETIC( 0x1000 ), //	Klasse ist vom Compiler generiert, im Quellcode nicht vorhanden.
	ACC_ANNOTATION( 0x2000 ), //	class-File beschreibt eine Annotation, keine Klasse. ACC_INTERFACE erforderlich.
	ACC_ENUM( 0x4000 ); //	Diese Klasse (oder ihre Superklasse) ist als Aufzählungstyp enum definiert.

	public final int flag;

	private Modifier( int flag ) {
		this.flag = flag;

	}

	public boolean isSetIn( int flags ) {
		return ( flags & flag ) == flag;
	}
}
