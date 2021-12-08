package Q2;

public class q2 {
    public static void applyTransformation(double[] arr, Transformer t)
    {
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = t.transform(arr[i]);
        }
    }
}
