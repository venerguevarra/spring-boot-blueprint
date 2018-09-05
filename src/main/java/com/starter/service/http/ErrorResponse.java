package com.starter.service.http;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(includeFieldNames = true)
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -2030738916643102217L;

    public ErrorResponse() {
    }

    public ErrorResponse(String status) {
        this.status = status;
    }

    @JsonProperty("status")
    private String status;

    private ErrorResponse(Builder builder) {
        this.status = builder.status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String status;

        private Builder() {
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }

}
