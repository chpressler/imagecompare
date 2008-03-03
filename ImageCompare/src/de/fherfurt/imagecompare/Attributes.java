package de.fherfurt.imagecompare;

public enum Attributes {
	
	path(ResourceHandler.getInstance().getStrings().getString("path")),
	keywords(ResourceHandler.getInstance().getStrings().getString("keywords")),
	cameratype(ResourceHandler.getInstance().getStrings().getString("cameratype")),
	filesize(ResourceHandler.getInstance().getStrings().getString("filesize")),
	createdate(ResourceHandler.getInstance().getStrings().getString("createdate")),
	changedate(ResourceHandler.getInstance().getStrings().getString("changedate")),
	makedate(ResourceHandler.getInstance().getStrings().getString("makedate")),
	pixelCount(ResourceHandler.getInstance().getStrings().getString("pixelCount")),
	exposureTime(ResourceHandler.getInstance().getStrings().getString("exposureTime")),
	fnumber(ResourceHandler.getInstance().getStrings().getString("fnumber")),
	focalLength(ResourceHandler.getInstance().getStrings().getString("focalLength")),
	flash(ResourceHandler.getInstance().getStrings().getString("flash")),
	iso(ResourceHandler.getInstance().getStrings().getString("iso")),
	stars(ResourceHandler.getInstance().getStrings().getString("stars")),
	avgSat(ResourceHandler.getInstance().getStrings().getString("avgSat")),
	avgLum(ResourceHandler.getInstance().getStrings().getString("avgLum")),
	contrast(ResourceHandler.getInstance().getStrings().getString("contrast")),
	dynamic(ResourceHandler.getInstance().getStrings().getString("dynamic")),
	faceCount(ResourceHandler.getInstance().getStrings().getString("faceCount"));
	
	public String getDesc() {
		return desc;
	}
	
	private Attributes(String desc) {
		this.desc = desc;
 	}
	
	private String desc;
	
}
