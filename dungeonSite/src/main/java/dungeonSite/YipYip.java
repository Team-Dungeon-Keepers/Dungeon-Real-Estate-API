package dungeonSite;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

//@Cacheable
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
@Entity
@Table(name = "\"YIPYIP\"")
public class YipYip implements Serializable{
	//no one really cares
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name = "message")
	private String message;
	
	
	
	public YipYip() 
	{
		id = 3;
	}
	public YipYip(String m) 
	{
		id = 3;
		this.message = m;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
