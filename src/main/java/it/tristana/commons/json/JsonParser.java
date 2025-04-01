package it.tristana.commons.json;
import java.util.HashSet;
import java.util.LinkedList;

public class JsonParser {

	private JsonParser() {}

	public static void main(String[] args) throws Exception {
		B[] bs = {
				new B(new String[][] { { "ciao\tmaxi" }, { "/summon Goblin" } }, -1.5e-7, new HashSet<>()),
				new B(new String[][] { { "123", "blabla" }, { "uno\n\rdue" } }, Double.NaN, new HashSet<>())
		};
		A a = new A(new short[] { 5, -16, 100 }, bs, "val");
		//System.out.println(toString(a));
		System.out.println(Condenser.objectFromString("{\"ciao\": [4, 6, 2], \"uno\": \"manu\"}", 1));
		//System.out.println("arrayFromString => " + Condenser.arrayFromString("[5,3,4]", 1));
		//System.out.println("elementFromString => " + Condenser.elementFromString("{\"ciao\": [4, 6, 2]}", 1));
		//System.out.println("parseValue => " + Condenser.parseValue("{\"ciao\": [4, 6, 2]}", 8));
	}

	public static String toString(Object object) throws Exception {
		return Stringifier.toString(object, new LinkedList<>());
	}
}
