package vip.acheng.audit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.acheng.audit.mapper.MovieMapper;
import vip.acheng.audit.model.MovieDO;
import vip.acheng.audit.service.MovieService;


/**
 * @author 清风徐来
 * @since 2022/8/31 11:22
 */
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, MovieDO> implements MovieService {

}
