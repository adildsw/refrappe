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

    public static String getProcessedUrl(String address, String port, String api) {
        String url = address;

        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        if (url.endsWith(":")) {
            url = url.substring(0, url.length() - 1);
        }
        url = url + ":" + port;
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        url = url + api;

        return url;
    }

    public static AppModel sampleApp() {
        try {
//            String compressed = "eJy1Vk2P2yAQ/SvIPewlTkM+nb0lq20v222lrtRDtQdiSIKCwQW8abTKf+8AsWMnjjartkgxZuZ5ePOAIa8RyfOYEkui21f/zml0G6Uq62asu9RgYfCLOt4nScbA+2WHZltmVMbQJ49AswrywrThSgIKd3vdHlgNlyvB4sLAp0siDOtEQqXEAioWXG4YrexSWb7cOaiubPAOMWNCqWbGuLj9iYvcxVHlzJW2zpP0BmAUxNiYUW5dZDweTqbJGOPRIBnsOy6zXEkmbZV0KzyZJsMhZJRa/sLtLjbsV8FkCin8jDLCZVx6YD5O8Sh6roU2LmwTBQa7y514TAZV44I3g4DvNHKpN5jR7GhekHSz0qqQ7psPS9/AnK65AJGk48jp0AcdBX7D6Hnf8cZ2Ioalbj1KHuFry62opv9eIUyxKD1Pa24QJUgzIhCFxxm5e99cMPbbxqkSSjtzz7czzmNPYOKfiX9OA/9e6LCTOSeAt2dq7UO6V+Y3quX3WGQLplH/NDdibwzSfLW2f5PWG4zHFxgvCmvrhMeHyWAwL11NTne+XeZUsQirC6cvdyfK84HBgUNt9zUQYRfDCZWs4cp5DC+FgPPETS7IrgyUazgO9hSbEiFKxOf7p1N3oQU4Pp6agTrJTBCzGa4wVmWV/3W/DxuoXdN0zdLNQv0+qjo5HrIwEGTBHIUH31+n5T5s1/Y5qVY5VdvaSib1OZOWOVXuF4RTE07FIGz+fugG7gwEiK8zzg/dCxGFCzmrBfzqUWgWGEIAX+0avjnAy0/nB9ygBXdXw91FTuarlZleUIbLvLBHWaZ1WaYtssDmStlaCeouh+hbbfQOMlBJ2tloQrk6svEVp6LjR28tE/Zf98+WB1+3PP06bH4Om79PdYwvJOqLyDFPfKwrT9ChVEkLodCaada9rOwClK+uaG6J4Gk1bCFz9b2D6xfPj7VSuWmWZU43aAulGfGbDFEF/y081f9YofGlS+X09vbQatc8su2772w8PtzT+NK1cC7YuCbYgxJNtXaqQKsgEtEUSbX9V0L5ZPfQ/gDyHDug";
//            String decompressed = decompressString(compressed, 2626);
//            String compressed = "eJzNVlFv2jAQ/itW9tAXwmIoBPoGVbeXrpu0SnuY+uDYBiycOIudUlTx33e2ISSBqHTTpEUiie++nL/7bN/xGpA8DxkxJLh5de+CBTcBVWk/5f1FARYOv6DnfBlJOXi/bNFsw7VKOfrkEGhWQZ55oYXKAIX7UT8CqxbZUvKw1PDpgkjNe4FUlBhAhVJka84qe6aMWGwttKhs8A4xQ8JYwbW2cQexjdzHQeXMVWGsZxINwSiJNiFnwtjIeHwdTydjjEfDyXDXs5nlKuOZqZJuwyfRNBoNozieQEbUiGdhtqHmv0qeUUjhZ5ASkYUHD8wnGB4FT7XQ2oZtosBgtrkVj2de1bAUzSDga0c+6A1mNDuaE0LXy0KVmf3mA+c0oQmY6UpIECmzHAW7dkFHnt918LTrOeN5IppTux4HHv5rI4yspv9eIXSZHDyPK6ERI6jgRCIGtxNyd+6ywfiLCamSqrDmyF0nnMeOQOzuE3efev6Rf2Arc04Ab07U2vl0L8xvVMvvoUwTXqBBOzdirjQqxHJl/iatNxiPOxgnpTF1wuP9ZDCYH1xNTrfu6uZUsfCrC6cvtyfK8YHBnkNt9zUQfhf7vV535SKEl1LCeRI6l2R7CJQXcBxMG0uJlAfE57vHtrssJDg+ts1AnaTai9kMV2qj0sr/utv5DXReU7ridJ2ol6Oq8fGQ+YEkCbcU7t3zMi13fruen5MVKmdqU1vJSX3OyZk5Ve4WRDDtT8XQb/6BfwztGfAQV2esHx7PRJY25KwW8KtDoZlnCAFctWv45gA/fDrf44ZncLc13G1gZb5YmWmHMiLLS3OUZVqXZXpGFthclK+UZLY5BN9qo3eQgUpynk1BmFBHNq7iVHTc6K1lwu7rwcny4MuWZ1CHzU9h8/epjnFHoq6IHPPEx7ryCA9EVWYgFFrxgve7lU1A+apFC0OkoNXwDJmL+w6uN54fK6Vy3SzLgq3RBkozElcpYgr+Wziq/7BC466m0u7e+9q43zUPfNPdsxfuOul/eLzv07irLZwKNq4Jdq9kU62tKtHSi0QKhjK1OaGSUJ4soks7NI4bLdil7Bl3Fd12I8Pxm52MDOIBHVx0rF36f9bK2n+2/t+eBtdvE6OyTQ==";
//            String decompressed = decompressString(compressed, 2984);
            String compressed = "eJy1VUtzmzAQ/isMvRrXYJfYuaVpmh6SSafOTKfTyUEG2dZESKok4tIM/727EsYY200fUx0Q2v1Y7X774DkkSkU5sSQ8f3bvLA/PQ1MQbTlbrW04cFJBCgry2yq42FAjCxq81yCnwYVSDeSJasOkAFQ8HA1HIDVMrDiNSgOfLgk3dBBymRELqIgz8UjzVi6kZcsKobqVwTvYjEiea2oM2p0lwzidDnEfz8IWoaS2qJ6OxiDkxNiI5syi+TidTOPR2ZvJZBaf1YMwk4WSggrbxnwEPsbHLIWwMsuemK0iQ7+VVGQQx9ewIExEW0340LFp0N6+GgS2UkgdFUvHWFSynXoQOrr3vwE2GrZBHFzsxAuSPa60LAV+82rpFoizNeNAkUDnWJ7EzuokfKj9ftwFQzPMw9YDBA5CyyxH4BzTH9w0+TflYqu4LI2VBfsBWeq7c+UWGqHfbZRJLjWKR24deJm6i+PEbynSqAho7QEbLor0RBSa5EzuYkh33HkHsBoWlMPZBRNcNlKpXBGy3Hh3PGfemzP3nLrnDB3zYJdcRLqa8UY/r6FsAPhEeImXbty59rY6uC+Uc7npACsvqP19HeRb1O9wC9xq708Hda0pFR3Yyp1r73EH9wlqeofScKrrkwlq+cdaqJv0HKc911LlctOpHpfJhvrm1IakkXqBLXxI/NgXQLNNDuged8O52eOwJTDeA91CL5dFB1d4Qd3c0IF+AMc6wDUe/4ygU3W5KK3tNlecNo0BJ5iYvArm1FoYj+agjy7dOt1He14AWyJSOB5dw8ChcYYo1lf6eQSTVtA9lYI2oqbkMBKZUZxUWxtKw2CzfWxGON8iPt7N7/v6UiO1r7PupNgDQACkML0x8NC/xX3eYoFkauw7N7LDOBm7NLlZ95vTzU3FF8fbvVzBD+tfZ1uSvDTOklON1a+bJNnVzXzDbLYO7sTrOzf2/7psHBf/sW6shF/qr8rm+upU1dg2AcdL5uUy8QbuoVrAnqOyESV4BuJqXD8B7bDfOw==";
            String decompressed = decompressString(compressed, 2295);
            return new AppModel(decompressed);
        } catch (JSONException e) {
            return null;
        }
    }
}
