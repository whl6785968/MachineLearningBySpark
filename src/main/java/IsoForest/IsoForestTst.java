package IsoForest;

import org.ejml.data.DenseMatrix64F;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IsoForestTst {
    public static void main(String[] args) throws IOException {
        IsoForest isoForest = new IsoForest();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\dell\\Desktop\\waterData\\IsoModel.txt"));
        String s = reader.readLine();
        IForest iForest = isoForest.load_model(s, IForest.class);

        String testPath = "C:\\Users\\dell\\Desktop\\waterData\\testForIsoForest.txt";

        List<DenseMatrix64F> list = new ArrayList<>();

        BufferedReader testReader = new BufferedReader(new FileReader(testPath));
        String line = null;
        while ((line = testReader.readLine()) != null) {
            String[] split = line.split(",");
            DenseMatrix64F denseMatrix64F = new DenseMatrix64F(1, split.length - 1);
            for (int i = 0; i < split.length - 1; i++) {
                denseMatrix64F.set(0, i, Double.parseDouble(split[i]));
            }
            list.add(denseMatrix64F);
        }

        int count = 0;
        List<DenseMatrix64F> bad_data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            double predict = iForest.predict(list.get(i));
            if (predict == -1.0) {
                count += 1;
                bad_data.add(list.get(i));
            }
        }

        System.out.println(count);
        System.out.println(bad_data.toString());

    }
}
