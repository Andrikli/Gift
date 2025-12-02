package repository;

import model.Candy;
import model.Sweet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemorySweetRepositoryTest {

    @Test
    void testMainFlow() {
        InMemorySweetRepository repo = new InMemorySweetRepository();

        Candy candy = Candy.builder().withName("C").withPrice(1).withWeightGram(1).build();

        repo.save(candy);
        assertEquals(1, repo.findAll().size());

        Sweet s2 = candy.toBuilder().withId(50).build();
        repo.save(s2);
        assertNotNull(repo.findById(50));

        assertNotNull(repo.findById(1));
        assertNull(repo.findById(999));

        Sweet updated = repo.findById(1).toBuilder().withName("New").build();
        assertTrue(repo.update(updated));

        assertFalse(repo.update(candy.toBuilder().withId(777).build()));
    }
}