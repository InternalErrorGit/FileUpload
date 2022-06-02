package ch.bbw.pg.fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class FileUploadController {

    @GetMapping
    public String main() {
        return "index";
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a .png image to upload.");
            return "redirect:/";
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (!fileName.contains(".png")) {
            redirectAttributes.addFlashAttribute("message", "Please select a .png image to upload.");
            return "redirect:/";
        }

        try {
            File f = new File("C://temp//" + fileName);
            file.transferTo(f);
            String text = "WATERmark";
            BufferedImage sourceImage = ImageIO.read(f);
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font("Arial", Font.BOLD, 64));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = sourceImage.getHeight() / 2;

            g2d.drawString(text, centerX, centerY);

            ImageIO.write(sourceImage, ".png", f);
            g2d.dispose();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // normalize the file path


        // save the file on the local file system
        //  try {
        //      Path path = Paths.get("C://temp//" + fileName);
        //      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        //  } catch (IOException e) {
        //      e.printStackTrace();
        //  }

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}