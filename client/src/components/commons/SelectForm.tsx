import { useState } from 'react';
import styled from 'styled-components';

interface StyledProps {
    height?: string;
}

interface SelectFormProps extends StyledProps {
    label: string;
    name: string;
    optionList: string[];
    handleSelect: (name: string, clickedOption: string | number | string[]) => void;
}

const SelectForm = ({ label, name, optionList, handleSelect, ...props }: SelectFormProps) => {
    const [selectOption, setSelectedOption] = useState(label);
    const [isFocused, setIsFocused] = useState(false);

    const handleClick = (option: string) => {
        setSelectedOption(option);
        handleSelect(name, option);
        setIsFocused(false);
    };

    return (
        <StyledSelectForm>
            <SelectBox
                className={`select-box__current ${isFocused ? 'focused' : ''}`}
                tabIndex={1}
                {...props}
                onClick={() => setIsFocused((prev) => !prev)}
            >
                <SelectedOption className="select-box__value">{selectOption}</SelectedOption>
                <DropDownIcon
                    className={`select-box__icon ${isFocused ? 'rotate' : ''}`}
                    src="http://cdn.onlinewebfonts.com/svg/img_295694.svg"
                    alt="Arrow Icon"
                    aria-hidden="true"
                />
            </SelectBox>
            <OptionList className={`select-box__list ${isFocused ? 'visible' : ''}`}>
                {optionList.map((option, index) => (
                    <Option key={`option_${index}`} onClick={() => handleClick(option)}>
                        {option}
                    </Option>
                ))}
            </OptionList>
        </StyledSelectForm>
    );
};

const StyledSelectForm = styled.div`
    position: relative;
    display: block;
    width: 100%;
`;

const SelectBox = styled.div<StyledProps>`
    cursor: pointer;
    outline: none;
    height: ${({ height }) => (height ? height : '50px')};
    display: flex;
    align-items: center;
    justify-content: center;
    border: 1px solid rgba(0, 0, 0, 0.3);
    border-radius: 0.5em;
    background-color: white;
    padding-right: 1.2em;

    img {
        width: 14px;
        height: 14px;
    }

    &.focused {
        border: 2px solid ${({ theme }) => theme.PRIMARY};
    }

    &.focused + .select-box__list {
        opacity: 1;
        animation-name: none;
    }

    &.focused .select-box__icon {
        transform: translateY(-50%) rotate(180deg);
    }

    @keyframes HideList {
        from {
            transform: scaleY(1);
        }
        to {
            transform: scaleY(0);
        }
    }
`;

const SelectedOption = styled.div`
    display: flex;
`;

const DropDownIcon = styled.img`
    position: absolute;
    top: 50%;
    right: 9px;
    transform: translateY(-50%);
    width: 20px;
    opacity: 0.3;
    transition: 0.2s ease;

    &.rotate {
        transform: translateY(-50%) rotate(180deg);
    }
`;

const OptionList = styled.ul`
    position: absolute;
    width: 100%;
    max-height: 250px;
    overflow: scroll;
    padding: 0;
    margin-top: 0.4em;
    z-index: 3;
    opacity: 0;
    animation-name: HideList;
    animation-duration: 1s;
    animation-delay: 0.1s;
    animation-fill-mode: forwards;
    animation-timing-function: step-start;
    box-shadow: 0 15px 30px -10px rgba(0, 0, 0, 0.1);
    border: 1px solid ${({ theme }) => theme.BORDER_LIGHT};
    border-radius: 0.5em;
    background-color: white;
`;

const Option = styled.li`
    display: block;
    padding: 15px;
    text-align: center;
    border-radius: 0.5em;
    cursor: pointer;

    &:hover,
    &:focus {
        color: ${({ theme }) => theme.PRIMARY};
        background-color: #fbfbfb;
    }
`;

export default SelectForm;
