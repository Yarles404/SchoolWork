package Q1;

import java.util.ArrayList;

public class Q1 {
    public static void main(String[] args) {
        System.out.println(factorization(2));
    }

    public static int smallestFactor(int n)
    {
        for (int i = 2; i < n; i++)
        {
            if (n % i == 0)
            {
                return i;
            }
        }

        return n;
    }

    public static ArrayList<Integer> factorization(int n)
    {
        ArrayList<Integer> factors = new ArrayList<>();
        if(smallestFactor(n) == n)
        {
            factors.add(n);
            return factors;
        }
        else
        {
            factors.add(smallestFactor(n));
            n /= smallestFactor(n);
            factors.addAll(factorization(n));
            return factors;
        }
    }
}
