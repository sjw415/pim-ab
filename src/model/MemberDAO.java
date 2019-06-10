package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.Member;
import model.MemberFileWriter;

public class MemberDAO {
	private ArrayList<Member> memberList = null;
	/*
	 * Database연결 (Connection), File I/O
	 */
	private File file = null;
	private MemberFileReader fr = null;
	private MemberFileWriter fw = null;
	
	
	//현재tab 기호를 이용하여 저장
	public MemberDAO(File file) {	
		this.file = file;
		try {
			fr = new MemberFileReader(file);
			memberList = fr.readMember();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//file i/o를 데이터베이스 처리하는 방식으로 처리하여 ArrayList<Member> 컬렉션 객체를 사용함
	public ArrayList<Member> selectAll() { 
		// 입력한 메모리 상에 존재하는 모든 멤버 정보를 가져와 출력			
		return memberList;	
	}
	
	public Member selectMember(Member member) {
		int index = -1;
		if((index = searchByID(member)) >= 0)
			return memberList.get(index);
		else
			return null;
	}
	//유일키를 이용하여 검색하여 인덱스를 반환
	public int searchByID(Member member) { 
		int ret = -1; // ret가 0 이상이면 검색 성공, -1 이면 검색 실패
		int index = 0;
	/*	for(int i=0; i<memberList.size();i++){
			Member m = memberList.get(i);
			if(m.getUid().equals(member.getUid())) {
				ret = index;
				break;
			}
			
			index++;
	}*/
		for(Member m : memberList) { //개선된 for문 memberList를 순차적으로 가져온다.
			if(m.getUid().equals(member.getUid())) {
				ret = index;
				break;
			}
			
			index++;
		}				
		return ret;
	}//aaaa
	
	public int insert(Member member) {
		int ret = -1;
		try {
			int index = searchByID(member);
			if(index < 0) { // -1이면 검색 실패, 등록 가능함
				fw = new MemberFileWriter(file);
				memberList.add(member);
				/*
				 * ArrayList 객체를 작업에 따라 수정하고, 이를 MemberFileWriter 객체의 saveMember() 메소드에 전달
				 */
				fw.saveMember(memberList);
				ret = 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return ret;
	}
	
	public int update(Member member) {
		int ret = -1; // 0 이상이면 해당 아이디가 존재하므로 수정, -1이하이면 수정 실패		
		
		
		return ret;//ss
	}	
	public int delete(Member member) {		
		int ret = -1;
		try {
			int index = searchByID(member);
			if(index > 0) { // 등록이 되어 있어야 삭제가 가능하므로 0이상이여야 한다.
				fw = new MemberFileWriter(file);
				memberList.remove(index);//member가아닌 index를 이용하여 삭제를 한다.
				fw.saveMember(memberList);
				ret = 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return ret;
	}
	public void printMemberList() {
		for(Member m : memberList)
			System.out.println(m.getUname() + ":" + m.getUid());
	}
}
