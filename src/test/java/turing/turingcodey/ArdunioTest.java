package turing.turingcodey;

import com.alibaba.fastjson.JSONObject;
import turing.turingcodey.core.utils.CompileUtils;

public class ArdunioTest {
    public static void main(String[] args) {
//        String cmd = "arduino_debug "+"--verify "+"/Blink.ino";
//        try {
//            Runtime.getRuntime().exec(cmd);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        String code = "void setup() {\n" +
                "  // initialize serial communication at 9600 bits per second:\n" +
                "  Serial.begin(9600);\n" +
                "}\n" +
                "\n" +
                "// the loop routine runs over and over again forever:\n" +
                "void loop() {\n" +
                "  // read the input on analog pin 0:\n" +
                "  int sensorValue = analogRead(A0);\n" +
                "  // print out the value you read:\n" +
                "  Serial.println(sensorValue);\n" +
                "  delay(1);        // delay in between reads for stability\n" +
                "}";
       JSONObject result = CompileUtils.compile(code);
        System.out.println("result:"+result);
    }
}
