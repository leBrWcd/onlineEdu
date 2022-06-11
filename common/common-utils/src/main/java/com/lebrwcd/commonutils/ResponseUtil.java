package com.lebrwcd.commonutils;/**
 * @author lebrwcd
 * @date 2022/6/5
 * @note
 */

/**
 * ClassName ResponseUtils
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/6/5
 */
import com.lebrwcd.commonutils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {

    public static void out(HttpServletResponse response, R r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

