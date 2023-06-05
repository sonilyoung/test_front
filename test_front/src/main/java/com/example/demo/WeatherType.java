package com.example.demo;

public enum WeatherType {

	FINE("1","/home/jun/apps/riskfree/webapps/static_file/fine.png"),//맑음
	CLOUDY("2","/home/jun/apps/riskfree/webapps/static_file/cloudy.png"),//구름
	PARTIALLY_CLOUDY("3","/home/jun/apps/riskfree/webapps/static_file/partially_cloudy.png"),//구름낀 맑음
	RAINY("4","/home/jun/apps/riskfree/webapps/static_file/rainy.png"),//비
	SNOWY("5","home/jun/apps/riskfree/webapps/static_file/snowy.png");//눈
	
    private String code;
    private String url;
    
    WeatherType(String code, String url) {
        this.code = code;
        this.url = url;
    }
	
    public String getCode() {
        return this.code;
    }
    
    public String getUrl() {
        return this.url;
    }
}