package io.github.mroncatto.itflow.domain.email.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.mroncatto.itflow.domain.email.model.vo.EmailTemplate;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailSendEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailTemplate template;

    @Column(nullable = false)
    private String subject;

    @OneToMany(mappedBy = "sendEvent", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnoreProperties(value = "sendEvent")
    @ToString.Exclude
    private List<EmailSendRecipient> recipients;

    @OneToMany(mappedBy = "sendEvent", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnoreProperties(value = "sendEvent")
    @ToString.Exclude
    private List<EmailEventData> eventDataList;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;

    private boolean sent;

    public String[] getAddress() {
        return recipients.stream()
                .map(EmailSendRecipient::getRecipient)
                .collect(Collectors.joining(";"))
                .split(";");
    }

    @PrePersist
    public void persistElement(){
        recipients.forEach(e -> e.setSendEvent(this));
        eventDataList.forEach(e -> e.setSendEvent(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EmailSendEvent that = (EmailSendEvent) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}