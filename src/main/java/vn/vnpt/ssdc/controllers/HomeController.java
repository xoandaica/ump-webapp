package vn.vnpt.ssdc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vn.vnpt.ssdc.api.client.DeviceApiClient;
import vn.vnpt.ssdc.dto.AcsResponse;
import vn.vnpt.ssdc.model.Device;
import vn.vnpt.ssdc.model.LinkModel;
import vn.vnpt.ssdc.model.PageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vietnq on 10/20/16.
 */
@Controller
public class HomeController {

    @Autowired
    private DeviceApiClient deviceApiClient;

    @Value("${limitRecordPerpage}")
    private String limit;

    @Value("${maxPageShow}")
    private String maxPageShow;

    public static final int FIRST_PAGE = 1;


    @GetMapping("/")
    public String index(Model model) {
        Map<String, String> indexParams = indexParams();
        int limitRecordPerPage = Integer.parseInt(limit);
        int limitPageShow = Integer.valueOf(maxPageShow);
        int currentPage = 1;
        PageModel pageModel = new PageModel();
        pageModel.setCurrentPage(FIRST_PAGE);
        pageModel.setFistPage(true);
        // list cac link se hien thi
        List<LinkModel> lstPageModel = new ArrayList<LinkModel>();
        //query devices from acs
        Map<String, String> query = new HashMap<String, String>();
        query.put("parameters", String.join(",", indexParams.keySet()));
        query.put("limit", limit);
        query.put("offset", "0");
        //TODO add paging param
        AcsResponse response = deviceApiClient.findDevices(query);
        List<Device> devices = Device.fromJsonString(response.body, indexParams.keySet());

        // count so page can hien thi
        int numberPage = response.nbOfItems / limitRecordPerPage;
        // VD 102 /20  = 5 du 1 thi so trang  = 6
        if (response.nbOfItems % limitRecordPerPage != 0) {
            numberPage += 1;
        }

        pageModel.setMaxPage(numberPage);
        if (numberPage < limitPageShow) {
            // chi co duy nhat 1 trang
            if (numberPage == FIRST_PAGE) {
                pageModel.setFistPage(true);
                pageModel.setLastPage(true);
                pageModel.setHasNext(false);
                pageModel.setHasPrevious(false);
                // set link hien thi
                LinkModel link = new LinkModel("/getPage?indexPage=" + FIRST_PAGE + "&limit=" + limit, String.valueOf(FIRST_PAGE));
                link.setActive(true);
                lstPageModel.add(link);


            }
            // co nhieu hon 1 trang nhung nho hon so cac trang can hien thi
            if (numberPage > 1 && numberPage < limitPageShow) {
                pageModel.setFistPage(true);
                pageModel.setLastPage(false);
                pageModel.setHasNext(false);
                pageModel.setHasPrevious(false);

                for (int i = 1; i <= numberPage; i++) {
                    LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
                    if (i == currentPage) {
                        link.setActive(true);
                    }
                    lstPageModel.add(link);
                }
                pageModel.setNext(FIRST_PAGE + 1);

            }

        } else if (numberPage > limitPageShow) {
            // nhieu hon so trang can hien thi
            pageModel.setHasNext(true);
            pageModel.setFistPage(true);
            pageModel.setLastPage(false);
            pageModel.setHasPrevious(false);
            for (int i = 1; i <= limitPageShow; i++) {
                LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
                if (i == currentPage) {
                    link.setActive(true);
                }
                lstPageModel.add(link);
            }
            pageModel.setNext(FIRST_PAGE + 1);

        } else {
            // bang dung so trang can hien thi
            pageModel.setHasNext(false);
            pageModel.setFistPage(true);
            pageModel.setLastPage(false);
            pageModel.setHasPrevious(false);
            for (int i = 1; i <= limitPageShow; i++) {
                LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
                if (i == currentPage) {
                    link.setActive(true);
                }
                lstPageModel.add(link);
            }
            pageModel.setNext(FIRST_PAGE + 1);
        }

        model.addAttribute("total", response.nbOfItems);
        model.addAttribute("indexParams", indexParams);
        model.addAttribute("devices", devices);
        // add number Page showed
        model.addAttribute("listPageModel", lstPageModel);
        model.addAttribute("pageModel", pageModel);
        // data for select box
        List<LinkModel> listSelectBox = new ArrayList<>();
        for (int i = 1; i <= numberPage; i++) {
            LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
            if (i == currentPage)
                link.setActive(true);
            listSelectBox.add(link);
        }
        model.addAttribute("selectBoxList", listSelectBox);
        return "index";
    }

    @RequestMapping(value = "/getPage", params = {"indexPage", "limit"}, method = RequestMethod.GET)
    public String indexPaging(@RequestParam("indexPage") String indexPage, @RequestParam("limit") String limit, Model model) {
        Map<String, String> indexParams = indexParams();
        Map<String, String> query = new HashMap<String, String>();

        int limitRecordPerPage = Integer.parseInt(limit);
        int limitPageShow = Integer.valueOf(maxPageShow);
        int currentPage = Integer.parseInt(indexPage);
        List<LinkModel> lstPageModel = new ArrayList<LinkModel>();
        //query devices from acs
        if (limitRecordPerPage == 0) {
            limitRecordPerPage = Integer.valueOf(this.limit);
        }
        int offset = (currentPage - 1) * limitRecordPerPage;


        query.put("parameters", String.join(",", indexParams.keySet()));
        query.put("limit", String.valueOf(limitRecordPerPage));
        query.put("offset", String.valueOf(offset));
        //TODO add paging param
        AcsResponse response = deviceApiClient.findDevices(query);
        List<Device> devices = Device.fromJsonString(response.body, indexParams.keySet());

        int numberPage = response.nbOfItems / limitRecordPerPage;
        if (response.nbOfItems % limitRecordPerPage != 0) {
            numberPage += 1;
        }
        System.out.println("numberPage" + numberPage);

        PageModel pageModel = new PageModel();
        pageModel.setCurrentPage(currentPage);
        pageModel.setNext(currentPage + 1);
        pageModel.setPrevious(currentPage - 1);
        pageModel.setMaxPage(numberPage);
        if (numberPage < limitPageShow) {
            // chi co duy nhat 1 trang
            if (numberPage == FIRST_PAGE) {
                pageModel.setFistPage(true);
                pageModel.setLastPage(true);
                pageModel.setHasNext(false);
                pageModel.setHasPrevious(false);
                // set link hien thi
                LinkModel link = new LinkModel("/getPage?indexPage=" + FIRST_PAGE + "&limit=" + limit, String.valueOf(FIRST_PAGE));
                link.setActive(true);
                lstPageModel.add(link);
            }
            // co nhieu hon 1 trang nhung nho hon so cac trang can hien thi
            if (numberPage > 1 && numberPage < limitPageShow) {
                int pageNeedShow = numberPage;
                for (int i = 1; i <= pageNeedShow; i++) {
                    LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
                    if (i == currentPage) {
                        link.setActive(true);
                    }
                    lstPageModel.add(link);
                }

                pageModel.setNext(currentPage + 1);
                pageModel.setPrevious(currentPage - 1);
                // khi curremt page la trang cuoi cung
                if (currentPage == numberPage) {
                    pageModel.setLastPage(true);
                    pageModel.setHasPrevious(false);
                    pageModel.setHasNext(false);
                }
            }
        } else if (numberPage > limitPageShow) {
            // nhieu hon so trang can hien thi
            if (currentPage == FIRST_PAGE) {
                pageModel.setFistPage(true);
                pageModel.setLastPage(false);
                pageModel.setHasNext(true);
                pageModel.setHasPrevious(false);
                for (int i = 1; i <= limitPageShow; i++) {
                    LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
                    if (i == currentPage) {
                        link.setActive(true);
                    }
                    lstPageModel.add(link);
                }
            }
            if (currentPage > FIRST_PAGE && currentPage <= limitPageShow) {
                pageModel.setFistPage(false);
                pageModel.setLastPage(false);
                pageModel.setHasNext(true);
                pageModel.setHasPrevious(false);
                for (int i = 1; i <= limitPageShow; i++) {
                    LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
                    if (i == currentPage) {
                        link.setActive(true);
                    }
                    lstPageModel.add(link);
                }
            }
            if (currentPage > limitPageShow && currentPage < numberPage) {
                pageModel.setFistPage(false);
                pageModel.setLastPage(false);
                pageModel.setHasNext(true);
                pageModel.setHasPrevious(true);
                for (int i = 1 + (currentPage - limitPageShow); i <= limitPageShow + (currentPage - limitPageShow); i++) {
                    LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
                    if (i == currentPage) {
                        link.setActive(true);
                    }
                    lstPageModel.add(link);
                }
            }
            if (currentPage == numberPage) {
                pageModel.setFistPage(false);
                pageModel.setLastPage(true);
                pageModel.setHasNext(false);
                pageModel.setHasPrevious(true);
                for (int i = 1 + (currentPage - limitPageShow); i <= limitPageShow + (currentPage - limitPageShow); i++) {
                    LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
                    if (i == currentPage) {
                        link.setActive(true);
                    }
                    lstPageModel.add(link);
                }
            }

        } else {
            // bang dung so trang can hien thi
            pageModel.setHasNext(false);
            pageModel.setHasPrevious(false);

            for (int i = 1; i <= limitPageShow; i++) {
                LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
                if (i == currentPage) {
                    link.setActive(true);
                }
                lstPageModel.add(link);
            }
            pageModel.setNext(currentPage + 1);
            pageModel.setPrevious(currentPage - 1);
            if (currentPage == FIRST_PAGE) {
                pageModel.setFistPage(true);
                pageModel.setLastPage(false);
                pageModel.setHasNext(false);
                pageModel.setHasPrevious(false);
            }
            if (currentPage == numberPage) {
                pageModel.setFistPage(false);
                pageModel.setLastPage(true);
                pageModel.setHasNext(false);
                pageModel.setHasPrevious(false);
            }

        }

        model.addAttribute("total", response.nbOfItems);
        model.addAttribute("indexParams", indexParams);
        model.addAttribute("devices", devices);
        // add number Page showed
        model.addAttribute("listPageModel", lstPageModel);
        model.addAttribute("pageModel", pageModel);

        // data for select box
        List<LinkModel> listSelectBox = new ArrayList<>();
        for (int i = 1; i <= numberPage; i++) {
            LinkModel link = new LinkModel("/getPage?indexPage=" + i + "&limit=" + limit, String.valueOf(i));
            if (i == currentPage)
                link.setActive(true);
            listSelectBox.add(link);
        }
        model.addAttribute("selectBoxList", listSelectBox);
        return "index";
    }


    //returns a map containing friendly name and actual path for parameters shown in device index view
    //TODO move this to configuration
    private Map<String, String> indexParams() {
        return new HashMap<String, String>() {{
            put("_deviceId._SerialNumber", "Serial Number");
            put("_deviceId._Manufacturer", "Manufacturer");
            put("_deviceId._ProductClass", "Product Class");
            put("_lastInform", "Last Inform");
        }};
    }


}
