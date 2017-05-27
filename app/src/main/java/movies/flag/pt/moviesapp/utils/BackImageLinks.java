package movies.flag.pt.moviesapp.utils;

import java.util.Random;

/**
 * Image Array used by the Movie Adapters
 */

public class BackImageLinks {
    public String[] backImages = new String[]{
            "https://3.bp.blogspot.com/-_nzMibLtqLY/WRscPcczaOI/AAAAAAAACYU/Rw8HICxRXwwqucMVj0NHzkF5hGRtBFplgCLcB/s320/1.jpg",
            "https://2.bp.blogspot.com/-LyOiBUEHCCo/WRscQ4LJnFI/AAAAAAAACY4/FI0aPeAyLBAye3EjHZXT4bsmNI366hkFQCLcB/s320/2.jpg",
            "https://4.bp.blogspot.com/-aUBMj4N7LuI/WRscSUAnR0I/AAAAAAAACZk/tp95HDXVwCU-VGtDjw3IOC9QPMewnx01ACLcB/s320/3.jpg",
            "https://1.bp.blogspot.com/-RtUUxeggSc0/WRscTxlS9vI/AAAAAAAACaQ/f_0c4jvhncUmi4sJiB0pCk70Juita8puACLcB/s320/4.jpg",
            "https://2.bp.blogspot.com/-z6XxK68gZXg/WRscVQi1TeI/AAAAAAAACa8/xBzcjV11MVgpPIOFn5YM3--kmUiNq0lBwCLcB/s320/5.jpg",
            "https://4.bp.blogspot.com/-LMlaubv4v4o/WRscWpG_VKI/AAAAAAAACbk/KZ96XE-flysKGwWH2qslUrvyOKM5uyXDACLcB/s320/6.jpg",
            "https://4.bp.blogspot.com/-b9dXDc_ctHo/WRscWuCIRcI/AAAAAAAACbg/2vNuu81M9nkLzPu-hjFmB21m2VIgEvzmgCLcB/s320/7.jpg",
            "https://4.bp.blogspot.com/-muJXN1jut9A/WRscW5e4TnI/AAAAAAAACbo/Es4VHr_aDlQPt--qWX3cQ1_1MqGP8RpgACLcB/s320/8.jpg",
            "https://2.bp.blogspot.com/-enL_zEz9uMk/WRscW2JFdAI/AAAAAAAACbs/z6Fl5zHF8YEfxBqRy_30y34QG6YRjIzCQCLcB/s320/9.jpg",
            "https://2.bp.blogspot.com/-78CHH9gdomU/WRscPWjdJEI/AAAAAAAACYM/L7M26djaolEvGYBilRUnGWNs-ta7uH7vgCLcB/s320/10.jpg",
            "https://4.bp.blogspot.com/-8dlBmGudEsk/WRscPbkAAxI/AAAAAAAACYQ/s6WPxQrQGQIm0yhkeF7HEslBaBIx91__wCLcB/s320/11.jpg",
            "https://3.bp.blogspot.com/-3S4TOpaOuO8/WRscP0KctbI/AAAAAAAACYc/45gCd_H2kbMCrvMacJufe0vymifPK3iLwCLcB/s320/12.jpg",
            "https://3.bp.blogspot.com/-nUjAVKxmr5I/WRscP_5w5xI/AAAAAAAACYY/SSyNKh_4aVQh0vuKLuyDmo8VfuMK5uiuwCLcB/s320/13.jpg",
            "https://2.bp.blogspot.com/-2RtYDFETnYc/WRscQIts73I/AAAAAAAACYg/FTt7btOnOZkSG8bR22551b3ETDajlf2gwCLcB/s320/14.jpg",
            "https://2.bp.blogspot.com/-N-WhjKfJt08/WRscQUnzTwI/AAAAAAAACYk/lge_gZA9tCM7YS6LUKGGiueB6wajRtOrQCLcB/s320/15.jpg",
            "https://2.bp.blogspot.com/-efZfC4Nb4kY/WRscQQFouQI/AAAAAAAACYo/gv_Uv65uxAwiwcag4Y-oN9crrrF1r9yOwCLcB/s320/16.jpg",
            "https://3.bp.blogspot.com/-t5bRQ-8XS2A/WRscQoJTReI/AAAAAAAACYs/JFRK0hmqCIYAudIAIpwjt7DtKqXPsvFTwCLcB/s320/17.jpg",
            "https://1.bp.blogspot.com/-K70BwHITzyI/WRscQqYHtZI/AAAAAAAACYw/5H8U-o8DfLAuNSIOo9bAAZt2oMNw_MBfwCLcB/s320/18.jpg",
            "https://1.bp.blogspot.com/-Y5C30zipEjg/WRscQ25NW0I/AAAAAAAACY0/7mGmcXuNT0s--AWxFyGx42vPv2mRdMzlACLcB/s320/19.jpg",
            "https://3.bp.blogspot.com/-xp1lPvOHJ7A/WRscRCbWApI/AAAAAAAACY8/1ROlfHWPjQgc9NuzGvMajyWn3jIqpnwgQCLcB/s320/20.jpg",
            "https://1.bp.blogspot.com/-mXPMF2FgcBI/WRscRRZ3vHI/AAAAAAAACZA/_SgkizJjQnUkvbsNLPGgjh6rb56m-wydgCLcB/s320/21.jpg",
            "https://4.bp.blogspot.com/-8M3uz_whq2Q/WRscRaBkUUI/AAAAAAAACZE/Yz_CzqIULiY42GaPHNOcKWzfnP3HhtjzQCLcB/s320/22.jpg",
            "https://2.bp.blogspot.com/-Cwvs-CM2JPs/WRscSx9ERjI/AAAAAAAACZ0/E3lu6AMwfaMW2lgJh_KAmZt69yIqs3r_ACLcB/s320/33.jpg",
            "https://3.bp.blogspot.com/-6UXl4b0N-aM/WRscTJMsuXI/AAAAAAAACZ8/ZnVo7pCAO3wi0L24ZNfS3Auly_jM9by5wCLcB/s320/34.jpg",
            "https://3.bp.blogspot.com/-ptUHFlDTsrs/WRscTESL7MI/AAAAAAAACZ4/3Ewwr1epdSEqPvle6pEzob1WDs64OxgfwCLcB/s320/35.jpg",
            "https://3.bp.blogspot.com/-AioCVZlZ8A4/WRscTXOn7GI/AAAAAAAACaA/wuCqtnoli_gyvRmwCAK6hiXsyLQTWWM8wCLcB/s320/36.jpg",
            "https://2.bp.blogspot.com/-leoLSGt0a6o/WRscTunBa8I/AAAAAAAACaE/iJZze-X-MtMVVBw5AprzUdRrMTKgx4Y-ACLcB/s320/37.jpg",
            "https://1.bp.blogspot.com/-7xMa4_vgsiI/WRscTlNrePI/AAAAAAAACaI/qyzKLxZpwi0Dme3vdkgfHir38vYI3E-ugCLcB/s320/38.jpg",
            "https://1.bp.blogspot.com/-24nMSFLAkvc/WRscTld7l_I/AAAAAAAACaM/KjpCGPLToHwH_wg5OW_DtzFzPTHjUqWLwCLcB/s320/39.jpg",
            "https://3.bp.blogspot.com/-VgvGMQa7qY0/WRscUCvUnCI/AAAAAAAACaU/fWBOzHrkxGAxFJUeb3_2X3CsIPikhWmKQCLcB/s320/40.jpg",
            "https://2.bp.blogspot.com/-y2ooZ3kZYPs/WRscUI-2pLI/AAAAAAAACaY/EjW1Sp3DNOQEut3cCKHvKpKqs4VIqYQ-QCLcB/s320/41.jpg",
            "https://4.bp.blogspot.com/-ko3oEx00eEA/WRscUaSbmHI/AAAAAAAACac/K_OgBrYVWjUC3WZwF4XgcEugrWZuG8JfwCLcB/s320/42.jpg",
            "https://2.bp.blogspot.com/-LQGPqepNdVo/WRscUf8y1DI/AAAAAAAACag/2z42VOwBpjMQ437XOIhgLjf3xYbMajuBwCLcB/s320/43.jpg",
            "https://4.bp.blogspot.com/-i76xwBGAFPI/WRscUgee5SI/AAAAAAAACak/vyRtcinVbe4ZbnKCtGz7twdDZ314PLwqwCLcB/s320/44.jpg",
            "https://3.bp.blogspot.com/-662vp4YZU7k/WRscU-uNFII/AAAAAAAACao/XAsxm-f_2WwI78yfiQEKncUICLnwcd8GgCLcB/s320/45.jpg",
            "https://4.bp.blogspot.com/-b0Stzl4ncCw/WRscU8duBVI/AAAAAAAACas/WiRf5ty1hcwYNhvgIaaj4TYKTcea2QQfQCLcB/s320/46.jpg",
            "https://3.bp.blogspot.com/-GhC3sAsXmUI/WRscU5MUnAI/AAAAAAAACaw/t2WPTHD7NpMtouFP54a-njQpRA7aVZlmQCLcB/s320/47.jpg",
            "https://1.bp.blogspot.com/-xU5VcOIQ9ng/WRscVOHHznI/AAAAAAAACa0/fp10WaaCfjk9ccKZZaJ7elN_lE941bKdwCLcB/s320/48.jpg",
            "https://1.bp.blogspot.com/-tGcQ-Zghw-A/WRscVIu3FJI/AAAAAAAACa4/jeCAmP-tDAcp71UlXyYX1Lbj2u040R_LwCLcB/s320/49.jpg",
            "https://2.bp.blogspot.com/-WAXtVs0bExM/WRscVppdNNI/AAAAAAAACbA/vKyziI2nmPkH18jk5mvmSupnj1xzh76UACLcB/s320/50.jpg",
            "https://2.bp.blogspot.com/-45qWVorjaSE/WRscVodYQvI/AAAAAAAACbE/aETjoZF3BV0eVDh5Oxyre0pk1UMBKOJsACLcB/s320/51.jpg",
            "https://4.bp.blogspot.com/-rnfqV1p_Z3A/WRscV-MgDSI/AAAAAAAACbI/y5XaRa4aKN4XzqpHNETZvtk_HMYCemv7wCLcB/s320/52.jpg",
            "https://1.bp.blogspot.com/-Qf75YYqAeJY/WRscV8zMRVI/AAAAAAAACbM/ML1cu30o0KYVvmpxXCjiu41ajIQThnvAQCLcB/s320/53.jpg",
            "https://4.bp.blogspot.com/-sc6O62fQK4Y/WRscWKH30HI/AAAAAAAACbQ/3u5R5nQLNAcPLs6kTXYhuYADXciG15TPgCLcB/s320/54.jpg",
            "https://4.bp.blogspot.com/-xyXQFRNo7HE/WRscWPTG-SI/AAAAAAAACbU/hI-5-spJ02Akgvo6fZdRuujo1gRegww_gCLcB/s320/55.jpg",
            "https://1.bp.blogspot.com/-PekgpXOmOuE/WRscWdl_bzI/AAAAAAAACbY/j0kmkUDclFgtU5M3f_HAcU2-LUPR6dxJwCLcB/s320/56.jpg",
            "https://1.bp.blogspot.com/-sLhLt25Xgm8/WRscWcHq8tI/AAAAAAAACbc/qxQxXqL32JUnzCG3ihykqYlY2tHsA7-2ACLcB/s320/57.jpg"
    };

    public String backImage() {
        final Random rand = new Random();
        String randomLink = backImages[rand.nextInt(backImages.length)];
        return randomLink;
    }
}
