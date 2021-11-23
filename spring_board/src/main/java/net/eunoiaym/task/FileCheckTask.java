package net.eunoiaym.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.controller.UploadController;
import net.eunoiaym.domain.AttachVo;
import net.eunoiaym.mapper.AttachMapper;

@Component @Log4j @AllArgsConstructor
public class FileCheckTask {
	private AttachMapper attachMapper; 
	
	private Set<String> getFolderBefore(List<AttachVo> fileList) {
		return new HashSet<>(fileList.stream().map(vo -> vo.getPath()).collect(Collectors.toList()));
		
	}
	
	@Scheduled(cron="0 0 2 * * *")
	public void checkFiles() {
		// DB list
		List<AttachVo> fileList = attachMapper.getOldFiles();
		if(fileList == null) return;
		
		List<Path> fileListPaths = 
				fileList
				.stream()
				.map(vo -> 
						Paths.get(UploadController.getUploadFolder(), vo.getFullPath()))
				.collect(Collectors.toList());

		// thumbnail 여부확인 후 예상파일목록에 섬네일 파일도 추가
		
		fileList
		.stream()
		.filter(vo->vo.isImage())
		.map(vo-> Paths.get(UploadController.getUploadFolder(),vo.getThumb()))
		.forEach(fileListPaths::add);
		
		// 물리적 파일 list
		
		getFolderBefore(fileList).forEach(folder -> {
			File targetDir = Paths.get(UploadController.getUploadFolder(), folder).toFile();
			File[] removeFiles = targetDir.listFiles(file->!fileListPaths.contains(file.toPath()));
			Arrays.asList(removeFiles).forEach(file->file.delete());
			
 		});
		
	}
}
