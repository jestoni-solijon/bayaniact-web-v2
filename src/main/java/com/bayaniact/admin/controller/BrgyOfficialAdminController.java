package com.bayaniact.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.bayaniact.common.entity.BrgyOfficial;
import com.bayaniact.common.file.BrgyOfficialFileService;
import com.bayaniact.common.service.BrgyOfficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/dashboard/brgy-official")
public class BrgyOfficialAdminController {

    private static final String BRGY_OFFICIAL_MODEL_KEY = "brgyOfficial";
    private static final String BRGY_OFFICIALS_MODEL_KEY = "brgyOfficials";
    private static final String BRGY_OFFICIAL_ROOT_PATH = "/brgy-official";
    private static final String BRGY_OFFICIAL_LIST = "brgy-official-list";
    private static final String BRGY_OFFICIAL_FORM = "brgy-official-form";
    private static final String BRGY_OFFICIAL_FORM_PATH = "/form";
    private static final String BRGY_OFFICIAL_SAVE_PATH = "/save";
    private static final String BRGY_OFFICIAL_LIST_PATH = "/list";
    private static final String BRGY_OFFICIAL_DELETE_PATH = "/delete";
    private static final String BRGY_OFFICIAL_ID_PARAM = "brgyOfficialId";

    @Autowired
    private BrgyOfficialService brgyOfficialService;

    @Autowired
    private BrgyOfficialFileService brgyOfficialFileService;

    @GetMapping(value = BRGY_OFFICIAL_FORM_PATH)
    public String getBrgyOfficialForm(Model model) {

        model.addAttribute("brgyOfficial", new BrgyOfficial());
        return "admin/brgy-official-form";
    }

    @GetMapping(value = BRGY_OFFICIAL_LIST_PATH)
    public String getBrgyOfficialList(@RequestParam(name = BRGY_OFFICIAL_ID_PARAM, required = false) Long brgyOfficialId,
                                   Model model) {

        model.addAttribute("brgyOfficial", new BrgyOfficial());

        if (brgyOfficialId != null) {
            Optional<BrgyOfficial> brgyOfficial = brgyOfficialService.findById(brgyOfficialId);
            model.addAttribute(BRGY_OFFICIAL_MODEL_KEY, brgyOfficial);
            return "admin/brgy-official-list";
        }

        model.addAttribute(BRGY_OFFICIALS_MODEL_KEY, brgyOfficialService.findAll());
        return "admin/brgy-official-list";
    }

    @PostMapping("/save")
    public String saveBrgyOfficial(@Valid @ModelAttribute BrgyOfficial brgyOfficial,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "file", required = false) MultipartFile file,
                                   Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return "admin/brgy-official-form";
        }

        BrgyOfficial savedBrgyOfficial = brgyOfficialService.save(brgyOfficial);

        System.out.println(file);
        if (file != null && !file.isEmpty()) {
            brgyOfficialFileService.saveFile(file, savedBrgyOfficial);
        }

        return "redirect:/dashboard/brgy-official/list";
    }

    @PostMapping("/update")
    public String updateBrgyOfficial(@Valid @ModelAttribute BrgyOfficial brgyOfficial,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "file", required = false) MultipartFile file,
                                   Model model) throws IOException {

        BrgyOfficial savedBrgyOfficial = brgyOfficialService.save(brgyOfficial);

        if (file != null && !file.isEmpty()) {
            brgyOfficialFileService.saveFile(file, savedBrgyOfficial);
        }

        return "redirect:/dashboard/brgy-official/list";
    }

    @PostMapping(value = BRGY_OFFICIAL_DELETE_PATH)
    public String deleteResident(@RequestParam(name = BRGY_OFFICIAL_ID_PARAM) List<Long> brgyOfficialId, RedirectAttributes redirectAttributes) {

        if (brgyOfficialId.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No Brgy Official selected for deletion.");
        } else {
            brgyOfficialService.deleteBrgyOfficials(brgyOfficialId);
            redirectAttributes.addFlashAttribute("message", "Selected Barangay Official has been successfully removed from the records.");
        }

        return "redirect:/dashboard/brgy-official/list";
    }
}