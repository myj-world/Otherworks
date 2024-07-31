package screens.legal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookOpen
import compose.icons.fontawesomeicons.solid.MailBulk
import compose.icons.fontawesomeicons.solid.ShieldAlt
import screens.assets.CopyrightMessage
import screens.device
import screens.poppins
import screens.resources.Resources

object PPTC : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "PPTC"
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.ShieldAlt)
            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        @Composable
        fun subHeading(subheading: String) {
            Text(
                text = subheading,
                fontSize = 28.sp,
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold
            )
        }

        @Composable
        fun body(content: String) {
            Text(
                text = content,
                fontSize = 18.sp,
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(30.dp))
        }

        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(31, 31, 54)
                )
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Privacy \n\n& Terms",
                fontSize = 48.sp,
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(20.dp))
            subHeading("Introduction")
            body("Accorm gathers & displays top study resources for specific subjects. We consider it our duty to provide everyone with a central hub for learning. We also make it our job to credit the providers for their hard work, but this does not mean we tend to promote them. By using Accorm and the provided services, you agree to abide to our policies. This website is the original work of its developers, made from scratch, and so there is no plagiarism involved.")
            subHeading("Rights & Responsibilities")
            body("By using Accorm's services, users agree: NOT to spam/scam or submit abusive, unethical, or illegal content NOT to submit any malicious or virus-containing files or links to credit the author of resources appropriately NOT to copy, share, resell or re-use any content or code of the website thereof.")
            subHeading("Data Collection")
            body("We do not intend to harm your data in anyways, or use it inappropriately or illegally. However, we collect data for tracking web traffic, managing your account, and managing website content. We use your email to control your account and provide you with your data. We display your name, profile picture and email address.")
            subHeading("Content Credits Policy")
            body("A major policy to consider is to give credits to the rightful author of the resource(s) properly. However, there are some websites that we do not keep any content from without approval(such as SaveMyExams), due to certain reasons such as disallowance for re-using their content elsewhere. All the content we use here is either free for the public, or we have taken permission from the legal authors. If the ultimate author finds their content being used without their permission or have any other issues, they can directly contact us through contact@ginastic.co.")
            subHeading("Changes & Updates")
            body("Being a user of this website, you agree thereof to comply with any changes to the policies. We will update the policies based on new scenarios observed and basing on any new feature developed on the website. Therefore, we do not intend to keep changing the policy on set intervals, but rather on situations.")
            subHeading("Contact")
            body("If you face any difficulty, problem, or have any concern, please do not hesitate to contact us at our email.")
            val navigator = LocalNavigator.currentOrThrow
            Button(
                onClick = {
                    navigator.push(Contact)
                },
                modifier = Modifier
                    .height(50.dp)
                    .clip(RoundedCornerShape(100)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(144, 144, 214)
                )
            ) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.MailBulk,
                    contentDescription = "Email",
                    tint = Color.White,
                    modifier = Modifier.height(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Email",
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(30.dp))
            CopyrightMessage()
            Spacer(Modifier.height(70.dp))
        }
    }
}