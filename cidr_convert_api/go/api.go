package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

// The Response type
type Response struct {
	Function string `json:"function,omitempty"`
	Input    string `json:"input,omitempty"`
	Output   string `json:"output,omitempty"`
}

// Display all from the people var
func routeGetHealth(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "OK")
}

// e.g. http://127.0.0.1:8000/cidr-to-mask?value=8
func routeGetCidrToMask(w http.ResponseWriter, r *http.Request) {
	value := r.URL.Query().Get("value")
	res := Response{
		"cidrToMask",
		value,
		cidrToMask(value),
	}
	json.NewEncoder(w).Encode(res)
}

// e.g. http://127.0.0.1:8000/mask-to-cidr?value=255.0.0.1
func routeGetMaskToCidr(w http.ResponseWriter, r *http.Request) {
	value := r.URL.Query().Get("value")
	res := Response{
		"maskToCidr",
		value,
		maskToCidr(value),
	}
	json.NewEncoder(w).Encode(res)
}

func routeIpv4Validation(w http.ResponseWriter, r *http.Request) {
	value := r.URL.Query().Get("value")
	res := Response{
		"ipv4Validation",
		value,
		strconv.FormatBool(ipv4Validation(value)),
	}
	json.NewEncoder(w).Encode(res)
}

// main function to boot up everything
func main() {
	router := mux.NewRouter()
	router.HandleFunc("/", routeGetHealth).Methods("GET")
	router.HandleFunc("/_health", routeGetHealth).Methods("GET")
	router.HandleFunc("/cidr-to-mask", routeGetCidrToMask).Methods("GET")
	router.HandleFunc("/mask-to-cidr", routeGetMaskToCidr).Methods("GET")
	router.HandleFunc("/ip-validation", routeIpv4Validation).Methods("GET")

	log.Fatal(http.ListenAndServe(":8000", router))
}
