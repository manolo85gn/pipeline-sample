package com.wizeline;
import static com.wizeline.JsonUtil.*;
import static com.wizeline.Convert.*;
import static com.wizeline.Response.*;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
      System.out.println( "Listening on: http://localhost:8000/" );

      port(8000);
      get("/", App::routeRoot);
      get("/_health", App::routeRoot);
      get("/cidr-to-mask", App::routeCidrToMask, json());
      get("/mask-to-cidr", App::routeMaskToCidr, json());
      get("/ip-validation", App::routeMaskToCidr, json());
    }

    public static Object routeRoot(spark.Request req, spark.Response res) throws Exception {
      return "OK";
    }

    public static Object routeCidrToMask(spark.Request req, spark.Response res) throws Exception {
      String value = req.queryParams("value");
      Response r = new Response("cidrToMask", value, Convert.cidrToMask(value));
      res.type("application/json");
      return r;
    }

    public static Object routeMaskToCidr(spark.Request req, spark.Response res) throws Exception {
      String value = req.queryParams("value");
      Response r = new Response("maskToCidr", value, Convert.maskToCidr(value));
      res.type("application/json");
      return r;
    }

    public static Object routeIpv4Validation(spark.Request req, spark.Response res) throws Exception {
      String value = req.queryParams("value");
      Response r = new Response("ipv4Validation", value, String.valueOf(Convert.ipv4Validation(value)));
      res.type("application/json");
      return r;
    }
}
