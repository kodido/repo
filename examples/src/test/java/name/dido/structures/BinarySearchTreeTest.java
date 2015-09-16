package name.dido.structures;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class BinarySearchTreeTest {

	private BinarySearchTree<Integer> tree;

	@Before
	public void setUp() {
		this.tree = BinarySearchTree.<Integer>emptyTree();
	}
	
	@Test
	public void test() {
		Integer[] numbersToInsert = new Integer[]{5, 1, 34, 6421, 9, 18, 215, 3445, 43, 345};
		for (Integer integer : numbersToInsert) {
			tree.insert(integer);
		}
		
		final ArrayList<Integer> treeOrderedValues = new ArrayList<>();
		tree.traverse(integer -> treeOrderedValues.add(integer));
		
		Arrays.sort(numbersToInsert);
		assertEquals(Arrays.asList(numbersToInsert), treeOrderedValues);
	}

}
