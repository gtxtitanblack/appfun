package com.example.appfun.bean;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by zx on 2016/1/14.
 */

public
@Data
class HeroInfo {

    private ResultEntity result;

    public
    @Data
    static class ResultEntity {
        private int status;
        private int count;

        private List<HeroesEntity> heroes;

        public
        @Data
        @AllArgsConstructor
        @ToString
        @NoArgsConstructor
        static class HeroesEntity implements Serializable {
            private String name;
            private int id;
            private String url;
            private String localized_name;
        }
    }


}
