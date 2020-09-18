package exoscale.itsdangerous;

public enum Algorithm {
    SHA1   { public String toString() { return "HmacSHA1";   }},
    SHA256 { public String toString() { return "HmacSHA256"; }}
}