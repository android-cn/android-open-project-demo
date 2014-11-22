package com.grumoon.volleydemo.util;

public class Constants {
	
	public static final String DEFAULT_STRING_REQUEST_URL="";
	public static final String DEFAULT_JSON_OBJECT_REQUEST_URL="";
	public static final String DEFAULT_JSON_ARRAY_REQUEST_URL="";
	public static final String DEFAULT_XML_REQUEST_URL="";
	public static final String DEFAULT_POST_REQUEST_URL="";
	
	public static final String[] IMAGE_URLS = new String[] {
			// Light
			"http://car1.autoimg.cn/upload/2014/2/11/t_201402111825333964435.jpg",
			"http://car0.autoimg.cn/upload/spec/9096/t_20101214073223880240.jpg",
			"http://car0.autoimg.cn/upload/spec/9284/t_20110106142930239264.jpg",
			"http://car1.autoimg.cn/upload/2013/1/25/t_201301251712272433765.jpg",
			"http://car0.autoimg.cn/upload/2013/1/30/t_201301302219285573796.jpg",
			"http://car1.autoimg.cn/upload/spec/9545/t_201110102225232813796.jpg",
			"http://car1.autoimg.cn/upload/spec/10876/t_2012062514403995674.jpg",
			"http://car1.autoimg.cn/upload/spec/5530/t_20120507212144676272.jpg",
			"http://car1.autoimg.cn/upload/spec/4874/t_201110171618584443796.jpg",
			"http://car0.autoimg.cn/upload/2013/10/21/t_201310210028059593826.jpg",
			"http://car1.autoimg.cn/upload/2013/9/28/t_201309280037389984149.jpg",
			"http://car1.autoimg.cn/upload/spec/10342/t_201112291800143433450.jpg",
			"http://car0.autoimg.cn/upload/spec/8320/t_20110511201132670240.jpg",
			"http://car1.autoimg.cn/upload/2013/3/22/t_201303221949284434136.jpg",
			"http://car1.autoimg.cn/upload/2014/7/2/t_201407020742485224435.jpg",
			"http://car1.autoimg.cn/upload/2012/12/11/t_201212111644171964136.jpg",
			"http://car0.autoimg.cn/upload/2013/3/5/t_201303051824348154136.jpg",
			"http://car1.autoimg.cn/upload/2014/5/16/t_201405161809454464435.jpg",
			"http://car0.autoimg.cn/upload/2014/8/19/t_20140819084436814421811.jpg",
			"http://car1.autoimg.cn/upload/2014/8/19/t_2014081911104704226410.jpg",
			"http://car1.autoimg.cn/upload/2014/6/24/t_20140624120730477213.jpg",
			"http://car1.autoimg.cn/upload/spec/5742/t_20101115174607698123.jpg",
			"http://car1.autoimg.cn/upload/2013/4/28/t_201304281710418974136.jpg",
			"http://car0.autoimg.cn/upload/2014/5/7/t_201405072325573934218.jpg",
			"http://car1.autoimg.cn/upload/2014/8/19/t_20140819181852565376510.jpg",
			"http://car1.autoimg.cn/upload/spec/1395/t_1395692798646.jpg",
			"http://car0.autoimg.cn/upload/2013/7/1/t_201307011803041514435.jpg",
			"http://car1.autoimg.cn/upload/2012/10/24/t_201210240822196094122.jpg",
			"http://car1.autoimg.cn/upload/2014/7/14/t_20140714162225860264.jpg",
			"http://car0.autoimg.cn/upload/spec/4877/t_4877266003399.jpg",
			"http://car0.autoimg.cn/upload/2012/10/23/t_201210231943198854136.jpg",
			"http://car0.autoimg.cn/upload/2013/5/3/t_201305030825577183450.jpg",
			"http://car0.autoimg.cn/upload/spec/8681/t_20100930090532421264.jpg",
			"http://car1.autoimg.cn/upload/2012/10/24/t_201210240839192744122.jpg",
			"http://car0.autoimg.cn/upload/2013/5/27/t_201305271932408464435.jpg",
			"http://car1.autoimg.cn/upload/2014/3/31/t_201403311931495585104.jpg",
			"http://car0.autoimg.cn/upload/2013/12/6/t_20131206205225137213.jpg",
			"http://car0.autoimg.cn/upload/2014/7/15/t_2014071516505263174.jpg",
			"http://car0.autoimg.cn/upload/spec/5938/t_2010112209285574761.jpg",
			"http://car0.autoimg.cn/upload/2014/8/21/t_20140821091048382489911.jpg",
			"http://car1.autoimg.cn/upload/2013/3/26/t_20130326093121389264.jpg",
			"http://car0.autoimg.cn/upload/2014/7/1/t_201407012031599213655.jpg",
			"http://car0.autoimg.cn/upload/2013/3/5/t_20130305174721012264.jpg",
			"http://car0.autoimg.cn/upload/2012/10/25/t_20121025091350047264.jpg",
			"http://car1.autoimg.cn/upload/2013/7/31/t_201307311819505814136.jpg",
			"http://car0.autoimg.cn/upload/2014/5/19/t_201405192106422244972.jpg",
			"http://car0.autoimg.cn/upload/spec/12871/t_201206061825195064122.jpg",
			"http://car1.autoimg.cn/upload/2014/8/21/t_2014082109142464726410.jpg",

			// heavy
			"http://car0.autoimg.cn/upload/2014/10/23/u_2014102313230389926411.jpg",
			"http://car0.autoimg.cn/upload/2014/8/18/u_2014081810042023326410.jpg",
			"http://car0.autoimg.cn/upload/2013/10/19/u_20131019092121453264.jpg",
			"http://car0.autoimg.cn/upload/2013/9/7/u_20130907093342885264.jpg",
			"http://car0.autoimg.cn/upload/2014/4/20/u_20140420163240895264.jpg",
			"http://car0.autoimg.cn/upload/2014/6/13/u_20140613090135001264.jpg",
			"http://car0.autoimg.cn/upload/2014/6/13/u_20140613090120119264.jpg",
			"http://car0.autoimg.cn/upload/spec/7330/u_20100316161623301264.jpg",
			"http://car0.autoimg.cn/upload/spec/11488/u_20111202154305788264.jpg",
			"http://car0.autoimg.cn/upload/2014/3/1/u_20140301102334017264.jpg",
			"http://car0.autoimg.cn/upload/2014/3/1/u_20140301102336835264.jpg",
			"http://car0.autoimg.cn/upload/2014/3/1/u_20140301102330133264.jpg",
			"http://car0.autoimg.cn/upload/2013/3/6/u_20130306211700674-1.jpg",
			"http://car0.autoimg.cn/upload/2013/3/5/u_20130305172315155-1.jpg",
			"http://car0.autoimg.cn/upload/2012/10/25/u_20121025091421755264.jpg",
			"http://car0.autoimg.cn/upload/2014/5/10/u_20140510090557148264.jpg",
			"http://car0.autoimg.cn/upload/2014/5/10/u_20140510090548233264.jpg",
			"http://car0.autoimg.cn/upload/2014/5/10/u_20140510090537329264.jpg",
			"http://car0.autoimg.cn/upload/2014/5/10/u_20140510090524740264.jpg",
			"http://car0.autoimg.cn/upload/2014/5/5/u_20140505230815771213.jpg",
			"http://car0.autoimg.cn/upload/2014/5/5/u_20140505230721705213.jpg",
			"http://car0.autoimg.cn/upload/2013/12/24/u_20131224085426775264.jpg",
			"http://car0.autoimg.cn/upload/2013/12/24/u_20131224085424068264.jpg",
			"http://car0.autoimg.cn/upload/2013/12/24/u_20131224085421096264.jpg",
			"http://car0.autoimg.cn/upload/2014/7/29/u_2014072909444980926410.jpg",
			"http://car0.autoimg.cn/upload/2014/7/29/u_2014072909444725926411.jpg",
			"http://car0.autoimg.cn/upload/2014/7/29/u_2014072909443932626410.jpg",
			"http://car0.autoimg.cn/upload/2013/10/26/u_20131026095433305264.jpg",
			"http://car0.autoimg.cn/upload/2013/7/31/u_201307311819117544136.jpg",
			"http://car0.autoimg.cn/upload/2013/7/31/u_201307311819400664136.jpg",
			"http://car0.autoimg.cn/upload/2013/7/31/u_201307311819016604136.jpg",
			"http://car0.autoimg.cn/upload/2013/4/20/u_20130420004358198264.jpg",
			"http://car0.autoimg.cn/upload/2013/4/20/u_20130420004346428264.jpg",
			"http://car0.autoimg.cn/upload/2013/4/20/u_20130420004333230264.jpg",
			"http://car0.autoimg.cn/upload/2013/4/12/u_20130412071552380264.jpg",
			"http://car0.autoimg.cn/upload/spec/4874/u_20100920093729529264.jpg",
			"http://car0.autoimg.cn/upload/spec/4874/u_4874535895853.jpg",
			"http://car0.autoimg.cn/upload/2012/12/4/u_20121204072530587264.jpg",
			"http://car0.autoimg.cn/upload/2012/10/24/u_201210240822196094122.jpg",
			"http://car0.autoimg.cn/upload/2012/10/23/u_201210231922466404122.jpg",
			"http://car0.autoimg.cn/upload/spec/5742/u_20101115174630370123.jpg",
			"http://car0.autoimg.cn/upload/spec/5742/u_20101115174617292123.jpg",
			"http://car0.autoimg.cn/upload/spec/5742/u_20101115174552057123.jpg",

			// very big
			"http://car0.autoimg.cn/upload/spec/12642/20120423080741086264.jpg",
			"http://car0.autoimg.cn/upload/spec/12642/20120423080735259264.jpg",
			"http://car0.autoimg.cn/upload/spec/12642/20120423080733177264.jpg",
			"http://car0.autoimg.cn/upload/2013/1/27/20130127152218734264.jpg",
			"http://car0.autoimg.cn/upload/spec/12210/20120306090400966264.jpg",
			"http://car0.autoimg.cn/upload/spec/12210/20120306090406799264.jpg",
			"http://car0.autoimg.cn/upload/spec/7647/20100422230408313264.jpg",
			"http://car0.autoimg.cn/upload/spec/7647/20100422230359547264.jpg",
			"http://car0.autoimg.cn/upload/2012/9/3/20120903095002336264.jpg",
			"http://car0.autoimg.cn/upload/2012/9/3/20120903094959185264.jpg",
			"http://car0.autoimg.cn/upload/2013/5/15/20130515215638105264.jpg",
			"http://car0.autoimg.cn/upload/2013/5/15/20130515215557342264.jpg",
			"http://car0.autoimg.cn/upload/2013/9/23/v_201309231938051564435.jpg",
			"http://car0.autoimg.cn/upload/2013/9/23/v_201309231937422494435.jpg",
			"http://car0.autoimg.cn/upload/2014/10/22/2014102209354546126410.jpg",
			"http://car0.autoimg.cn/upload/2014/10/22/2014102209354141126411.jpg",
			"http://car0.autoimg.cn/upload/2014/10/22/2014102209353819126410.jpg",
			"http://car0.autoimg.cn/upload/2014/10/22/2014102209352986726411.jpg",
			"http://car0.autoimg.cn/upload/2014/10/22/2014102209352831626410.jpg",
			"http://car0.autoimg.cn/upload/2014/2/9/20140209090503847264.jpg",
			"http://car0.autoimg.cn/upload/2014/2/9/20140209090502599264.jpg",
			"http://car0.autoimg.cn/upload/2013/11/28/20131128170140086264.jpg",
			"http://car0.autoimg.cn/upload/2013/11/28/20131128170138438264.jpg",
			"http://car0.autoimg.cn/upload/2013/11/28/20131128170133674264.jpg"

	};

	private Constants() {
	}

	
	public static class Extra {
		public static final String FRAGMENT_INDEX = "com.grumoon.volleydemo.FRAGMENT_INDEX";
		
	}

}
