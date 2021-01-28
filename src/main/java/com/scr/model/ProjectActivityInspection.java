package com.scr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "project_activity_inspection")
@NamedQuery(name = "ProjectActivityInspection.findAll", query = "SELECT  pai from ProjectActivityInspection pai")
public class ProjectActivityInspection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String status;
	
	private String division;
	
	private Date date;
	
	private String location;
	
	private String make;
	
	private String model;
	
	private String assetId;
	
	private String doneBy;
	
	private String approved;
	
	private String approvedBy;
	
	private String m1;

	private String m10;

	private String m11;

	private String m12;

	private String m13;

	private String m14;

	private String m15;

	private String m16;

	private String m17;

	private String m18;

	private String m19;

	private String m2;

	private String m20;

	private String m21;

	private String m22;

	private String m23;

	private String m24;

	private String m25;

	private String m26;

	private String m27;

	private String m28;

	private String m29;

	private String m3;

	private String m30;

	private String m31;

	private String m32;

	private String m33;

	private String m34;

	private String m35;

	private String m36;

	private String m37;

	private String m38;

	private String m39;

	private String m4;

	private String m40;

	private String m41;

	private String m42;

	private String m43;

	private String m44;

	private String m45;

	private String m46;

	private String m47;

	private String m48;

	private String m49;

	private String m5;

	private String m50;

	private String m51;

	private String m52;

	private String m53;

	private String m54;

	private String m55;

	private String m56;

	private String m57;

	private String m58;

	private String m59;

	private String m6;

	private String m60;

	private String m61;

	private String m62;

	private String m63;

	private String m64;

	private String m65;

	private String m66;

	private String m67;

	private String m68;

	private String m69;

	private String m7;

	private String m70;

	private String m8;

	private String m9;
	
	@Column(columnDefinition="TEXT")
	private String mma1;

	@Column(columnDefinition="TEXT")
	private String mma10;

	@Column(columnDefinition="TEXT")
	private String mma2;

	@Column(columnDefinition="TEXT")
	private String mma3;

	@Column(columnDefinition="TEXT")
	private String mma4;

	@Column(columnDefinition="TEXT")
	private String mma5;

	@Column(columnDefinition="TEXT")
	private String mma6;

	@Column(columnDefinition="TEXT")
	private String mma7;

	@Column(columnDefinition="TEXT")
	private String mma8;

	@Column(columnDefinition="TEXT")
	private String mma9;
	
	private String a1;

	private String a10;

	private String a100;

	private String a101;

	private String a102;

	private String a103;

	private String a104;

	private String a105;

	private String a106;

	private String a107;

	private String a108;

	private String a109;

	private String a11;

	private String a110;

	private String a111;

	private String a112;

	private String a113;

	private String a114;

	private String a115;

	private String a116;

	private String a117;

	private String a118;

	private String a119;

	private String a12;

	private String a120;

	private String a121;

	private String a122;

	private String a123;

	private String a124;

	private String a125;

	private String a126;

	private String a127;

	private String a128;

	private String a129;

	private String a13;

	private String a130;

	private String a14;

	private String a15;

	private String a16;

	private String a17;

	private String a18;

	private String a19;

	private String a2;

	private String a20;

	private String a21;

	private String a22;

	private String a23;

	private String a24;

	private String a25;

	private String a26;

	private String a27;

	private String a28;

	private String a29;

	private String a3;

	private String a30;

	private String a31;

	private String a32;

	private String a33;

	private String a34;

	private String a35;

	private String a36;

	private String a37;

	private String a38;

	private String a39;

	private String a4;

	private String a40;

	private String a41;

	private String a42;

	private String a43;

	private String a44;

	private String a45;

	private String a46;

	private String a47;

	private String a48;

	private String a49;

	private String a5;

	private String a50;

	private String a51;

	private String a52;

	private String a53;

	private String a54;

	private String a55;

	private String a56;

	private String a57;

	private String a58;

	private String a59;

	private String a6;

	private String a60;

	private String a61;

	private String a62;

	private String a63;

	private String a64;

	private String a65;

	private String a66;

	private String a67;

	private String a68;

	private String a69;

	private String a7;

	private String a70;

	private String a71;

	private String a72;

	private String a73;

	private String a74;

	private String a75;

	private String a76;

	private String a77;

	private String a78;

	private String a79;

	private String a8;

	private String a80;

	private String a81;

	private String a82;

	private String a83;

	private String a84;

	private String a85;

	private String a86;

	private String a87;

	private String a88;

	private String a89;

	private String a9;

	private String a90;

	private String a91;

	private String a92;

	private String a93;

	private String a94;

	private String a95;

	private String a96;

	private String a97;

	private String a98;

	private String a99;
	
	@ManyToOne
	@JoinColumn(name = "inspection_version_id", foreignKey = @ForeignKey(name = "fk_prj_act_ins_ins_versions"))
	private InspectionVersions inspectionVersionId;

}
