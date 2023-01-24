import torch  
import torchvision
from torch.utils.mobile_optimizer import optimize_for_mobile

from models.stylegan_generator import StyleGANGenerator

resolution = 1024
model = StyleGANGenerator(resolution)

# weights and bias sets
checkpoint_path = 'stylegan_ffhq1024.pth'  #To get at https://github.com/genforce/genforce
checkpoint = torch.load(checkpoint_path, map_location='cpu')
model.load_state_dict(checkpoint['generator_smooth'])

#model.cpu()
model.eval()

#input
test_sample_input_trace = torch.randn(1, 512)

scripted_model = torch.jit.trace(model, test_sample_input_trace)
print("torch.jit.trace  ",isinstance(scripted_model, torch.jit.ScriptModule)) 

opt_model = optimize_for_mobile(scripted_model)

output_path = "AndroidApp/app/src/main/assets/"
#opt_model.save(output_path + "stylegan_ffhq1024_input_z.pt")
opt_model._save_for_lite_interpreter(output_path + "stylegan_ffhq1024_input_z.ptl")




