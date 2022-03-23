package com.adildsw.frappe.utils;

import android.util.Log;

import com.adildsw.frappe.models.AppModel;

import org.json.JSONException;

import java.util.Base64;
import java.util.zip.Inflater;

public class DataUtils {

    public static String decompressString(String data, int length) {
        try {
            byte[] decodedString = Base64.getDecoder().decode(data.getBytes("UTF-8"));
            Inflater inflater = new Inflater();
            inflater.setInput(decodedString);
            byte[] result = new byte[length];
            int resultLength = inflater.inflate(result);
            inflater.end();
            Log.println(Log.ASSERT, "decompressed", new String(result, 0, resultLength));
            return new String(result, 0, resultLength, "UTF-8");
        }
        catch (Exception e) {
            Log.e("MainActivity", "Error decoding string", e);
            return null;
        }
    }
    public static void test() {
        String compressed2 = "eJx1kMFOwzAQRH8lMtc4SgohTW+5cOMLqh6MvSmrOutgO0VR1X9nHSClB1ayZL2ZHWnnItQ4SqOiErvL8kcjdkK7oRig6D0T4CfyRSM1AKuvc9Z9QnADZC+LI+tWyxl8QEfsqoqyKJkGpKMFOQVe7ZUNkAvrtIrskhbpBGbl5CL2c7L6lfGfM6UyxkMIKXfTpOSiEqs4Oh+Tsi0fGVoVogSDMSVXz09NW9VtW5Xb5pqny0ZHQHE9+h/7pq75Ih3xjHGWAT4mIM0n7MWgkOSvIg5/MkPKu5cZxHlMrQF91yknvMm5WPq+3+EifopmnHU3/Kb06ejdRGnnoV+GsX5Hy+1w6fvDlecLUXGboA==";
        String compressed = "eJy1Vk2P2yAQ/SvIPewlTkM+nb0lq20v222lrtRDtQdiSIKCwQW8abTKf+8AsWMnjjartkgxZuZ5ePOAIa8RyfOYEkui21f/zml0G6Uq62asu9RgYfCLOt4nScbA+2WHZltmVMbQJ49AswrywrThSgIKd3vdHlgNlyvB4sLAp0siDOtEQqXEAioWXG4YrexSWb7cOaiubPAOMWNCqWbGuLj9iYvcxVHlzJW2zpP0BmAUxNiYUW5dZDweTqbJGOPRIBnsOy6zXEkmbZV0KzyZJsMhZJRa/sLtLjbsV8FkCin8jDLCZVx6YD5O8Sh6roU2LmwTBQa7y514TAZV44I3g4DvNHKpN5jR7GhekHSz0qqQ7psPS9/AnK65AJGk48jp0AcdBX7D6Hnf8cZ2Ioalbj1KHuFry62opv9eIUyxKD1Pa24QJUgzIhCFxxm5e99cMPbbxqkSSjtzz7czzmNPYOKfiX9OA/9e6LCTOSeAt2dq7UO6V+Y3quX3WGQLplH/NDdibwzSfLW2f5PWG4zHFxgvCmvrhMeHyWAwL11NTne+XeZUsQirC6cvdyfK84HBgUNt9zUQYRfDCZWs4cp5DC+FgPPETS7IrgyUazgO9hSbEiFKxOf7p1N3oQU4Pp6agTrJTBCzGa4wVmWV/3W/DxuoXdN0zdLNQv0+qjo5HrIwEGTBHIUH31+n5T5s1/Y5qVY5VdvaSib1OZOWOVXuF4RTE07FIGz+fugG7gwEiK8zzg/dCxGFCzmrBfzqUWgWGEIAX+0avjnAy0/nB9ygBXdXw91FTuarlZleUIbLvLBHWaZ1WaYtssDmStlaCeouh+hbbfQOMlBJ2tloQrk6svEVp6LjR28tE/Zf98+WB1+3PP06bH4Om79PdYwvJOqLyDFPfKwrT9ChVEkLodCaada9rOwClK+uaG6J4Gk1bCFz9b2D6xfPj7VSuWmWZU43aAulGfGbDFEF/y081f9YofGlS+X09vbQatc8su2772w8PtzT+NK1cC7YuCbYgxJNtXaqQKsgEtEUSbX9V0L5ZPfQ/gDyHDug";
//        String decompressed2 = decompressString(compressed, 469);
        String decompressed = decompressString(compressed, 2626);

        AppModel app;
        try {
            app = new AppModel(decompressed);
        } catch (JSONException e) {
            e.printStackTrace();
            app = null;
        }
    }

    public static AppModel sampleApp() throws JSONException {
        String compressed = "eJy1Vk2P2yAQ/SvIPewlTkM+nb0lq20v222lrtRDtQdiSIKCwQW8abTKf+8AsWMnjjartkgxZuZ5ePOAIa8RyfOYEkui21f/zml0G6Uq62asu9RgYfCLOt4nScbA+2WHZltmVMbQJ49AswrywrThSgIKd3vdHlgNlyvB4sLAp0siDOtEQqXEAioWXG4YrexSWb7cOaiubPAOMWNCqWbGuLj9iYvcxVHlzJW2zpP0BmAUxNiYUW5dZDweTqbJGOPRIBnsOy6zXEkmbZV0KzyZJsMhZJRa/sLtLjbsV8FkCin8jDLCZVx6YD5O8Sh6roU2LmwTBQa7y514TAZV44I3g4DvNHKpN5jR7GhekHSz0qqQ7psPS9/AnK65AJGk48jp0AcdBX7D6Hnf8cZ2Ioalbj1KHuFry62opv9eIUyxKD1Pa24QJUgzIhCFxxm5e99cMPbbxqkSSjtzz7czzmNPYOKfiX9OA/9e6LCTOSeAt2dq7UO6V+Y3quX3WGQLplH/NDdibwzSfLW2f5PWG4zHFxgvCmvrhMeHyWAwL11NTne+XeZUsQirC6cvdyfK84HBgUNt9zUQYRfDCZWs4cp5DC+FgPPETS7IrgyUazgO9hSbEiFKxOf7p1N3oQU4Pp6agTrJTBCzGa4wVmWV/3W/DxuoXdN0zdLNQv0+qjo5HrIwEGTBHIUH31+n5T5s1/Y5qVY5VdvaSib1OZOWOVXuF4RTE07FIGz+fugG7gwEiK8zzg/dCxGFCzmrBfzqUWgWGEIAX+0avjnAy0/nB9ygBXdXw91FTuarlZleUIbLvLBHWaZ1WaYtssDmStlaCeouh+hbbfQOMlBJ2tloQrk6svEVp6LjR28tE/Zf98+WB1+3PP06bH4Om79PdYwvJOqLyDFPfKwrT9ChVEkLodCaada9rOwClK+uaG6J4Gk1bCFz9b2D6xfPj7VSuWmWZU43aAulGfGbDFEF/y081f9YofGlS+X09vbQatc8su2772w8PtzT+NK1cC7YuCbYgxJNtXaqQKsgEtEUSbX9V0L5ZPfQ/gDyHDug";
        String decompressed = decompressString(compressed, 2626);
        return new AppModel(decompressed);
    }
}
