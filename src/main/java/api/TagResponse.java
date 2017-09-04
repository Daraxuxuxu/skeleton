package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import generated.tables.records.TagsRecord;

import java.math.BigDecimal;
import java.sql.Time;

public class TagResponse {
    @JsonProperty
    Integer id;

    @JsonProperty
    Integer receiptId;

    @JsonProperty
    String tag;

    @JsonProperty
    Time created;

    public TagResponse(TagsRecord dbRecord) {
        this.receiptId = dbRecord.getReceiptid();
        this.tag = dbRecord.getTag();
        this.created = dbRecord.getUploaded();
        this.id = dbRecord.getId();
    }
}
