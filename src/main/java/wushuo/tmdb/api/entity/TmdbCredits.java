package wushuo.tmdb.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 演职人员
 */
@Data
@Accessors(chain = true)
public class TmdbCredits implements Serializable {
    private List<TmdbCreditsCast> cast;
}
