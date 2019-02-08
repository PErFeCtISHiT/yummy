package yummy.util;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * @author: pis
 * @description: good good study
 * @date: create in 20:47 2018/3/28
 */
public class JsonHelper {
    private static Logger logger = LoggerFactory.getLogger(JsonHelper.class);
    private static String dir = System.getProperty("user.dir") + "/annotator/";


    private JsonHelper() {
        throw new IllegalStateException(NamedContext.UTILCLASS);
    }



    public static JSONObject requestToJson(HttpServletRequest request) {
        StringBuilder jsonStr = new StringBuilder();
        try (InputStream inputStream = request.getInputStream()) {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                jsonStr.append(inputStr);
            }
            streamReader.close();
        } catch (IOException e) {
            logger.error(NamedContext.ERROR);
        }
        return new JSONObject(jsonStr.toString());
    }


    public static void jsonToResponse(HttpServletResponse response, JSONObject jsonObject) {
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.append(jsonObject.toString());
        } catch (IOException e) {
            logger.error(NamedContext.ERROR);
        }
    }
}
