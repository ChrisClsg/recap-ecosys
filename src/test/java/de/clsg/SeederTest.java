package de.clsg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SeederTest
{
    @Test
    public void seed_shouldAdd10Products()
    {
        ProductRepo pr = new ProductRepo();
        assertEquals(0, pr.getAll().size());
        Seeder.seed(pr);
        assertEquals(10, pr.getAll().size());
        assertNotNull(pr.getById("P001"));
    }
}
