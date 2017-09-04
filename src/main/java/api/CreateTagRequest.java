package api;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateTagRequest {
    @NotEmpty
    public Integer receiptId;

    //public String tag;
}
