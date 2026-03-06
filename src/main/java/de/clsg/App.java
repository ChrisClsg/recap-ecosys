package de.clsg;

public class App
{
    public static void main( String[] args ) {
        ProductRepo pr = new ProductRepo();
        Seeder.seed(pr);
    }
}
