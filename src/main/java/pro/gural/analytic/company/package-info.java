/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
package pro.gural.analytic.company;

import jakarta.persistence.*;
import org.apache.kafka.common.protocol.types.Field;
import pro.gural.common.domain.CompanyAddress;
import pro.gural.common.domain.CompanyStatusType;
import pro.gural.common.domain.KafkaActionType;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "company")
class CompanyEntity {
    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CompanyStatusType status;

    @Column(name = "contact_information")
    private String contactInformation;

    private String industry;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_action")
    private KafkaActionType eventAction;

    @Column(name = "event_time")
    private Instant eventTime;

    public String getId() {
        return id;
    }

    public CompanyEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CompanyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyStatusType getStatus() {
        return status;
    }

    public CompanyEntity setStatus(CompanyStatusType status) {
        this.status = status;
        return this;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public CompanyEntity setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
        return this;
    }

    public String getIndustry() {
        return industry;
    }

    public CompanyEntity setIndustry(String industry) {
        this.industry = industry;
        return this;
    }

    public KafkaActionType getEventAction() {
        return eventAction;
    }

    public CompanyEntity setEventAction(KafkaActionType eventAction) {
        this.eventAction = eventAction;
        return this;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    public CompanyEntity setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyEntity that = (CompanyEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", contactInformation='" + contactInformation + '\'' +
                ", industry='" + industry + '\'' +
                ", eventAction=" + eventAction +
                ", eventTime=" + eventTime +
                '}';
    }
}

class CompanyCurrentName {
    private String currentName;

    public String getCurrentName() {
        return currentName;
    }

    public CompanyCurrentName setCurrentName(String currentName) {
        this.currentName = currentName;
        return this;
    }

    @Override
    public String toString() {
        return "CompanyCurrentName{" +
                "currentName='" + currentName + '\'' +
                '}';
    }
}
