package controller;

import model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller<String> controller;
    private Model<String> model;

    @BeforeEach
    void setUp() {
        controller = new Controller<>("LL");
        model = controller.getModel(); // use the actual model
    }

    @Test
    void testAdd() {
        controller.add("A", true);
        assertTrue(model.getLL().contains("A"));
    }

    @Test
    void testInsert() {
        controller.add("B", false); // append first
        controller.insert("C", 0);
        assertEquals("C", model.getLL().get(0));
    }

    @Test
    void testRemove() {
        controller.add("D", false);
        controller.remove("D");
        assertFalse(model.getLL().contains("D"));
    }

    @Test
    void testFind() {
        controller.add("E", false);
        int index = (int) controller.find("E");
        assertEquals(0, index); // first element
    }

    @Test
    void testReset() {
        controller.add("F", false);
        controller.reset();
        assertTrue(model.getLL().isEmpty());
    }

    @Test
    void testSave() {
        assertDoesNotThrow(() -> controller.save());
    }

    @Test
    void testLoad() {
        assertDoesNotThrow(() -> controller.load());
    }
    
    @Test
    void testCompareSorts() {
        controller.compareSorts();
        Model<String> m = controller.getModel();
        assertTrue(m.hasInsertionSort());
        //assertTrue(m.hasBubbleSort());
    }
}
