package vn.vnpt.ssdc.api.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import vn.vnpt.ssdc.dto.AcsResponse;
import vn.vnpt.ssdc.model.Device;

import java.util.Map;

/**
 * Created by SSDC on 11/1/2016.
 */
public interface IDeviceService {

    AcsResponse findDevices(Map<String, String> queryMap);

}
