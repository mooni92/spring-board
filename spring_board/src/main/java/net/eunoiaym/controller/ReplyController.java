package net.eunoiaym.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.domain.ReplyCriteria;
import net.eunoiaym.domain.ReplyVo;
import net.eunoiaym.service.ReplyService;

@RestController
@AllArgsConstructor
@Log4j
@RequestMapping("/replies")
public class ReplyController {
	private ReplyService service;
	
	@PostMapping("new")
	public String create(@RequestBody ReplyVo vo) {
		log.info("create :: " + vo);
		service.register(vo);
		log.info(vo);
		return "success";
	}
	
	@GetMapping("{rno}")
	public ReplyVo get(@PathVariable Long rno){
		log.info("get.. :: " + rno);
		return service.get(rno);
	}
	
	@PutMapping("{rno}")
	public String modify(@PathVariable Long rno , @RequestBody ReplyVo vo) {
		log.info("modify :: " + vo);
		service.modify(vo);
		return "success";
	}
	
	@DeleteMapping("{rno}") 
	public String remove(@PathVariable Long rno) {
		log.info("remove :: " + rno);
		service.remove(rno);
		return "success";
	}
	
	@GetMapping({"{bno}/{amount}/{lastRno}"})
	public List<ReplyVo> getList(@PathVariable Long bno,
			@PathVariable(required=false) Optional<Long> lastRno, 
			@PathVariable(required=false) Integer amount) {
		return service.getList(new ReplyCriteria(lastRno.get(), amount) , bno);
	}
	
}
